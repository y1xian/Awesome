package com.yyxnb.arch.delegate

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.livedata.ViewModelFactory
import com.yyxnb.common.MainThreadUtils.post
import java.lang.reflect.Field

class FragmentDelegateImpl(
        private var fragmentManager: FragmentManager?,
        private var fragment: Fragment?
) : IFragmentDelegate, LifecycleObserver {

    private var iFragment: IFragment? = fragment as IFragment

    override fun onAttached(context: Context?) {}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreated(savedInstanceState: Bundle?) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initDeclaredFields()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        iFragment?.initView(savedInstanceState)
        //        iFragment.initViewData();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStarted() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResumed() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPaused() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStoped() {
    }

    override fun onSaveInstanceState(outState: Bundle?) {}

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onViewDestroyed() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroyed() {
        fragmentManager = null
        fragment = null
        iFragment = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDetached() {
    }

    override val isAdd: Boolean
        get() = fragment!!.isAdded

    fun initDeclaredFields() {
        post(Runnable {

            val declaredFields = iFragment!!.javaClass.declaredFields
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
                        field[iFragment] = getViewModel(field, annotation.isActivity)
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun getViewModel(field: Field, isActivity: Boolean): ViewModel {
        return if (isActivity) {
            ViewModelFactory.createViewModel(fragment!!.activity!!, field)
        } else {
            ViewModelFactory.createViewModel(fragment!!, field)
        }
    }
}