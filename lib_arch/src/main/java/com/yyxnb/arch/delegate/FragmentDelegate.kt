package com.yyxnb.arch.delegate

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.*
import com.yyxnb.arch.annotations.BarStyle
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.SwipeStyle
import com.yyxnb.arch.base.IActivity
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.common.ArchConfig
import com.yyxnb.arch.common.Bus
import com.yyxnb.arch.common.MsgEvent
import com.yyxnb.arch.livedata.ViewModelFactory
import com.yyxnb.common.MainThreadUtils
import com.yyxnb.common.StatusBarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import java.lang.reflect.Field

class FragmentDelegate(private var iFragment: IFragment?) : CoroutineScope by MainScope() {

    private var iActivity: IActivity? = null
    private var mRootView: View? = null
    private var mFragment: Fragment?
    private var mActivity: FragmentActivity? = null
    private val mLazyDelegate: FragmentLazyDelegate

    private var layoutRes = 0
    private var statusBarTranslucent = ArchConfig.statusBarTranslucent
    private var fitsSystemWindows = ArchConfig.fitsSystemWindows
    private var statusBarColor = ArchConfig.statusBarColor
    private var statusBarDarkTheme = ArchConfig.statusBarStyle
    private var swipeBack = SwipeStyle.Edge
    private var subPage = false
    private var needLogin = false

    init {
        mFragment = iFragment as Fragment?
        mLazyDelegate = FragmentLazyDelegate(mFragment)
    }

    fun onAttach(context: Context?) {
        mActivity = context as FragmentActivity
        require(mActivity is IActivity) { "Activity请实现IActivity接口" }
        iActivity = mActivity as IActivity
    }

    fun onCreate(savedInstanceState: Bundle?) {
        mLazyDelegate.onCreate(savedInstanceState)
        initAttributes()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == mRootView) {
            if (layoutRes != 0 || iFragment?.initLayoutResId() != 0) {
                mRootView = inflater.inflate(if (layoutRes == 0) iFragment!!.initLayoutResId() else layoutRes, container, false)
            }
        } else {
            //  二次加载删除上一个子view
            val viewGroup = mRootView as ViewGroup
            viewGroup.removeView(mRootView)
        }
        mRootView!!.setOnTouchListener { _: View?, event: MotionEvent? ->
            mActivity!!.onTouchEvent(event)
            false
        }
        return mRootView
    }

    fun onActivityCreated(savedInstanceState: Bundle?) {
        mLazyDelegate.onActivityCreated(savedInstanceState)
        setNeedsStatusBarAppearanceUpdate()
    }

    fun setUserVisibleHint(isVisibleToUser: Boolean) {
        mLazyDelegate.setUserVisibleHint(isVisibleToUser)
    }

    fun onHiddenChanged(hidden: Boolean) {
        mLazyDelegate.onHiddenChanged(hidden)
    }

    fun onConfigurationChanged(newConfig: Configuration?) {
        mLazyDelegate.onConfigurationChanged(newConfig)
    }

    fun onResume() {
        mLazyDelegate.onResume()
    }

    fun onPause() {
        mLazyDelegate.onPause()
    }

    fun onDestroy() {
        mLazyDelegate.onDestroy()
        iFragment = null
        iActivity = null
        mActivity = null
        mFragment = null
        mRootView = null
    }

    fun onDestroyView() {
        // 取消协程
        if (isActive) {
            cancel()
        }
    }

    /**
     * 加载注解设置
     */
    fun initAttributes() {
        MainThreadUtils.post(Runnable {
            iFragment?.javaClass?.getAnnotation(BindRes::class.java)?.let {
                layoutRes = it.layoutRes
                fitsSystemWindows = it.fitsSystemWindows
                statusBarTranslucent = it.statusBarTranslucent
                swipeBack = it.swipeBack
                subPage = it.subPage
                if (it.statusBarStyle != BarStyle.None) {
                    statusBarDarkTheme = it.statusBarStyle
                }
                if (it.statusBarColor != 0) {
                    statusBarColor = it.statusBarColor
                }
                needLogin = it.needLogin
                // 如果需要登录，并且处于未登录状态下，发送通知
                if (needLogin && !ArchConfig.needLogin) {
                    Bus.post(MsgEvent(ArchConfig.NEED_LOGIN_CODE))
                }
            }
        })
    }

    /**
     * 更新状态栏样式
     */
    fun setNeedsStatusBarAppearanceUpdate() {
        // 子页面不做处理
        if (subPage) {
            return
        }
        // 侧滑返回
        iActivity!!.setSwipeBack(swipeBack)

        // 文字颜色
        val statusBarStyle = statusBarDarkTheme
        StatusBarUtils.setStatusBarStyle(window, statusBarStyle == BarStyle.DarkContent)

        // 隐藏 or 不留空间 则透明
        if (!fitsSystemWindows) {
            StatusBarUtils.setStatusBarColor(window, Color.TRANSPARENT)
        } else {
            var statusBarColor = statusBarColor

            //不为深色
            var shouldAdjustForWhiteStatusBar = !StatusBarUtils.isBlackColor(statusBarColor, 176)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                shouldAdjustForWhiteStatusBar = shouldAdjustForWhiteStatusBar && statusBarStyle == BarStyle.LightContent
            }
            //如果状态栏处于白色且状态栏文字也处于白色，避免看不见
            if (shouldAdjustForWhiteStatusBar) {
                statusBarColor = ArchConfig.shouldAdjustForWhiteStatusBar
            }
            StatusBarUtils.setStatusBarColor(window, statusBarColor)
        }
        StatusBarUtils.setStatusBarTranslucent(window, statusBarTranslucent, fitsSystemWindows)
    }

    val window: Window
        get() = mActivity!!.window

    fun getViewModel(field: Field, activity: Boolean): ViewModel {
        return if (activity) {
            ViewModelFactory.createViewModel(mActivity!!, field)
        } else {
            ViewModelFactory.createViewModel(mFragment!!, field)
        }
    }

    fun initArguments(): Bundle {
        var args = mFragment!!.arguments
        if (args == null) {
            args = Bundle()
            mFragment!!.arguments = args
        }
        return args
    }

    fun finish() {
        mActivity?.onBackPressed()
    }

    fun getLazyDelegate(): FragmentLazyDelegate = mLazyDelegate

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FragmentDelegate

        if (iFragment != other.iFragment) return false

        return true
    }

    override fun hashCode(): Int {
        return iFragment?.hashCode() ?: 0
    }


}