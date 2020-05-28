package com.yyxnb.arch.delegate

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.arch.base.IActivity
import com.yyxnb.arch.livedata.ViewModelFactory
import com.yyxnb.common.AppConfig.getInstance
import com.yyxnb.common.MainThreadUtils.post

class ActivityDelegateImpl(private var activity: FragmentActivity?) : IActivityDelegate, LifecycleObserver {

    private var iActivity: IActivity? = activity as IActivity

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate(savedInstanceState: Bundle?) {
        iActivity?.apply {
            initWindows()
            if (initLayoutResId() != 0) {
                activity?.setContentView(initLayoutResId())
            }
            initDeclaredFields()
            initView(savedInstanceState)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
    }

    override fun onSaveInstanceState(activity: Activity?, outState: Bundle?) {}

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        activity = null
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
                        field[iActivity] = ViewModelFactory.createViewModel(activity!!, field)
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}