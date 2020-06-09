package com.yyxnb.arch.delegate

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.EditText
import com.github.anzewei.parallaxbacklayout.ParallaxHelper
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout
import com.yyxnb.arch.ContainerActivity
import com.yyxnb.arch.annotations.BarStyle
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.SwipeStyle
import com.yyxnb.arch.base.IActivity
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.common.ArchConfig
import com.yyxnb.arch.common.Bus
import com.yyxnb.arch.common.MsgEvent
import com.yyxnb.common.MainThreadUtils.post
import com.yyxnb.common.StatusBarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive

class ActivityDelegate(private var iActivity: IActivity?) : CoroutineScope by MainScope() {

    private var mActivity: AppCompatActivity? = iActivity as AppCompatActivity

    private var layoutRes = 0
    private var statusBarTranslucent = ArchConfig.statusBarTranslucent
    private var fitsSystemWindows = ArchConfig.fitsSystemWindows
    private var statusBarColor = ArchConfig.statusBarColor
    private var statusBarDarkTheme = ArchConfig.statusBarStyle
    private var needLogin = false
    private var isExtends = false
    private var isContainer = false

    /**
     * 是否第一次加载
     */
    private var mIsFirstVisible = true

    fun onCreate(savedInstanceState: Bundle?) {
        initAttributes()
        if (!isExtends) {
            iActivity?.apply {
                if (layoutRes != 0 || initLayoutResId() != 0) {
                    mActivity?.setContentView(if (layoutRes == 0) initLayoutResId() else layoutRes)
                }
            }
        }
        initView()
    }

    private fun initView() {
        if (!isContainer) {
            // 不留空间 则透明
            if (!fitsSystemWindows) {
                StatusBarUtils.setStatusBarColor(getWindow(), Color.TRANSPARENT)
            } else {
                StatusBarUtils.setStatusBarColor(getWindow(), statusBarColor)
            }
            StatusBarUtils.setStatusBarStyle(getWindow(), statusBarDarkTheme == BarStyle.DarkContent)
            StatusBarUtils.setStatusBarTranslucent(getWindow(), statusBarTranslucent, fitsSystemWindows)
        }
    }

    private fun getWindow(): Window {
        return mActivity!!.window
    }

    fun onWindowFocusChanged(hasFocus: Boolean) {
        if (mIsFirstVisible && hasFocus) {
            mIsFirstVisible = false
            iActivity!!.initViewData()
            iActivity!!.initObservable()
        }
    }

    fun onDestroy() {
        mIsFirstVisible = true
        iActivity = null
        mActivity = null
        // 取消协程
        if (isActive) {
            cancel()
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {  //判断得到的焦点控件是否包含EditText
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            //得到输入框在屏幕中上下左右的位置
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right
                    && event.y > top && event.y < bottom)
        }
        // 如果焦点不是EditText则忽略
        return false
    }

    /**
     * 加载注解设置
     */
    fun initAttributes() {
        post(Runnable {
            iActivity?.javaClass?.getAnnotation(BindRes::class.java)?.let {
                layoutRes = it.layoutRes
                fitsSystemWindows = it.fitsSystemWindows
                statusBarTranslucent = it.statusBarTranslucent
                if (it.statusBarStyle != BarStyle.None) {
                    statusBarDarkTheme = it.statusBarStyle
                }
                if (it.statusBarColor != 0) {
                    statusBarColor = it.statusBarColor
                }
                needLogin = it.needLogin
                isExtends = it.isExtends
                isContainer = it.isContainer
                // 如果需要登录，并且处于未登录状态下，发送通知
                if (needLogin && !ArchConfig.needLogin) {
                    Bus.post(MsgEvent(ArchConfig.NEED_LOGIN_CODE))
                }
            }
        })
    }

    fun setSwipeBack(mSwipeBack: Int) {
        val layout = ParallaxHelper.getParallaxBackLayout(mActivity, true)
        when (mSwipeBack) {
            SwipeStyle.Full -> {
                ParallaxHelper.enableParallaxBack(mActivity)
                //全屏滑动
                layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_FULL)
            }
            SwipeStyle.Edge -> {
                ParallaxHelper.enableParallaxBack(mActivity)
                //边缘滑动
                layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_DEFAULT)
            }
            SwipeStyle.None -> ParallaxHelper.disableParallaxBack(mActivity)
            else -> {
            }
        }
    }

    fun <T : IFragment> startFragment(targetFragment: T) {
        startFragment(targetFragment, 0)
    }

    fun <T : IFragment> startFragment(targetFragment: T, requestCode: Int) {
        val intent = Intent(mActivity, ContainerActivity::class.java)
        val bundle: Bundle? = targetFragment.initArguments()
        bundle?.putInt(ArchConfig.REQUEST_CODE, requestCode)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(ArchConfig.FRAGMENT, targetFragment.javaClass.canonicalName)
        intent.putExtra(ArchConfig.BUNDLE, bundle)
        mActivity!!.startActivityForResult(intent, requestCode)
    }

    fun <T : IFragment> setRootFragment(fragment: T, containerId: Int) {
        val transaction = mActivity!!.supportFragmentManager.beginTransaction()
        transaction.replace(containerId, (fragment as Fragment), fragment.sceneId())
        transaction.addToBackStack(fragment.sceneId())
        transaction.commitAllowingStateLoss()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ActivityDelegate) return false

        if (iActivity != other.iActivity) return false

        return true
    }

    override fun hashCode(): Int {
        return iActivity?.hashCode() ?: 0
    }

}