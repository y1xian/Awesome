package com.yyxnb.arch.delegate

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.ViewGroup
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.arch.base.IActivity
import com.yyxnb.arch.livedata.ViewModelFactory
import com.yyxnb.arch.utils.AppManager
import com.yyxnb.common.MainThreadUtils.post

/**
 * ActivityLifecycleCallbacks 监听 Activity 生命周期
 * PS ：先走 ActivityLifecycleCallbacks 再走 Activity
 */
class ActivityDelegateImpl(
        private var mActivity: FragmentActivity?
) : IActivityDelegate {

    private var iActivity: IActivity? = mActivity as IActivity
    private var delegate: ActivityDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        iActivity?.apply {
            // 在界面未初始化之前调用的初始化窗口
            initWindows()
            delegate = iActivity?.getBaseDelegate()
            delegate?.onCreate(savedInstanceState)
            initDeclaredFields()
            initView(savedInstanceState)
        }
    }

    override fun onStart() {
        val view = (mActivity?.window?.decorView as ViewGroup).getChildAt(0)
        view.viewTreeObserver.addOnWindowFocusChangeListener { hasFocus: Boolean ->
            if (delegate != null) {
                delegate!!.onWindowFocusChanged(hasFocus)
            }
        }
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onSaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onDestroy() {
        delegate?.onDestroy()
        AppManager.activityDelegates?.remove(iActivity.hashCode())
        mActivity = null
        iActivity = null
    }

    fun initDeclaredFields() {
        post(Runnable {
            val declaredFields = iActivity!!.javaClass.declaredFields
            for (field in declaredFields) {
                // 允许修改反射属性
                field.isAccessible = true
                /**
                 * 根据 @BindViewModel 注解, 查找注解标示的变量（ViewModel）
                 * 并且 创建 ViewModel 实例, 注入到变量中
                 */
                val annotation = field.getAnnotation(BindViewModel::class.java)
                if (annotation != null) {
                    try {
                        field[iActivity] = ViewModelFactory.createViewModel(mActivity!!, field)
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}