package com.yyxnb.arch.base

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.github.anzewei.parallaxbacklayout.ParallaxBack
import com.github.anzewei.parallaxbacklayout.ParallaxHelper
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout
import com.yyxnb.arch.ContainerActivity
import com.yyxnb.arch.annotations.SwipeStyle
import com.yyxnb.arch.common.ArchConfig
import com.yyxnb.arch.delegate.ActivityDelegate
import com.yyxnb.arch.utils.FragmentManagerUtils
import com.yyxnb.common.KeyboardUtils
import me.jessyan.autosize.AutoSizeCompat
import java.lang.ref.WeakReference

/**
 * 建议 [ContainerActivity.initBaseFragment]  }
 */
@Deprecated("")
@ParallaxBack(edgeMode = ParallaxBack.EdgeMode.EDGE)
abstract class BaseActivity : AppCompatActivity(), IActivity {

    protected val TAG = javaClass.canonicalName
    protected var mContext: WeakReference<Context>? = null
    private val java8Observer: Java8Observer
    private val mActivityDelegate by lazy { ActivityDelegate(this) }

    val context: Context?
        get() = mContext!!.get()

    init {
        java8Observer = Java8Observer(TAG)
        lifecycle.addObserver(java8Observer)
    }

    override fun getBaseDelegate(): ActivityDelegate? {
        return mActivityDelegate
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawable(null)
        mContext = WeakReference(this)
        // 在界面未初始化之前调用的初始化窗口
//        initWindows()
        super.onCreate(savedInstanceState)
        mActivityDelegate.onCreate(savedInstanceState)
//        setContentView(initLayoutResId())
//        initView(savedInstanceState)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        mActivityDelegate.onWindowFocusChanged(hasFocus)
    }

    override fun setSwipeBack(mSwipeBack: Int) {
        val layout = ParallaxHelper.getParallaxBackLayout(this, true)
        when (mSwipeBack) {
            SwipeStyle.Full -> {
                ParallaxHelper.enableParallaxBack(this)
                //全屏滑动
                layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_FULL)
            }
            SwipeStyle.Edge -> {
                ParallaxHelper.enableParallaxBack(this)
                //边缘滑动
                layout.setEdgeMode(ParallaxBackLayout.EDGE_MODE_DEFAULT)
            }
            SwipeStyle.None -> ParallaxHelper.disableParallaxBack(this)
            else -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivityDelegate.onDestroy()
        lifecycle.removeObserver(java8Observer)
        mContext!!.clear()
        mContext = null
//        mActivityDelegate = null
    }

    override fun getResources(): Resources {
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
        //如果没有自定义需求用这个方法
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        //如果有自定义需求就用这个方法
//        AutoSizeCompat.autoConvertDensity(super.getResources(), 667f, false);
        return super.getResources()
    }

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
        val instance = FragmentManagerUtils
        if (instance.count() > 1) {
            val current = instance.currentFragment() as BaseFragment
            val before = instance.beforeFragment()
            //将回调的传入到fragment中去
            if (current != null && before != null) {
                before.onActivityResult(current.requestCode, current.resultCode, current.result)
            }
        }
        if (fragments.isEmpty()) {
            super.onBackPressed()
        } else {
            ActivityCompat.finishAfterTransition(this)
        }
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATED_IDENTITY_EQUALS", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        //把操作放在用户点击的时候
        if (event.action === MotionEvent.ACTION_DOWN) {
            val v = currentFocus      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (mActivityDelegate.isShouldHideKeyboard(v, event)) { //判断用户点击的是否是输入框以外的区域
                //收起键盘
                KeyboardUtils.hideSoftInput(v)
            }
        }
        return super.onTouchEvent(event)
    }

    fun <T : BaseFragment> startFragment(targetFragment: T) {
        startFragment(targetFragment, 0)
    }

    fun <T : BaseFragment> startFragment(targetFragment: T, requestCode: Int) {
        val intent = Intent(this, ContainerActivity::class.java)
        val bundle = targetFragment.initArguments()
        bundle.putInt(ArchConfig.REQUEST_CODE, requestCode)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(ArchConfig.FRAGMENT, targetFragment.javaClass.canonicalName)
        intent.putExtra(ArchConfig.BUNDLE, bundle)
        startActivityForResult(intent, requestCode)
    }

    fun setRootFragment(fragment: BaseFragment, containerId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment, fragment.sceneId())
        transaction.addToBackStack(fragment.sceneId())
        transaction.commitAllowingStateLoss()
    }

}