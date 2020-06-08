package com.yyxnb.arch.delegate

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.livedata.ViewModelFactory
import com.yyxnb.arch.utils.AppManager
import com.yyxnb.common.MainThreadUtils.post
import java.lang.reflect.Field

/**
 * FragmentLifecycleCallbacks 监听 Fragment 生命周期
 * PS ：先走 Fragment 再走 FragmentLifecycleCallbacks
 */
class FragmentDelegateImpl(
        private var fragmentManager: FragmentManager?,
        private var fragment: Fragment?
) : IFragmentDelegate {

    private var iFragment: IFragment? = fragment as IFragment
    private var delegate: FragmentDelegate? = null

    override fun onAttached(context: Context?) {
        delegate = iFragment!!.getBaseDelegate()
        delegate?.onAttach(context)
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        delegate?.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initDeclaredFields()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        delegate?.onActivityCreated(savedInstanceState)
    }

    override fun onStarted() {
    }

    override fun onResumed() {
        delegate?.onResume()
    }

    override fun onPaused() {
        delegate?.onPause()
    }

    override fun onStopped() {
    }

    override fun onSaveInstanceState(outState: Bundle?) {}

    override fun onViewDestroyed() {
        delegate?.onDestroyView()
    }

    override fun onDestroyed() {
        delegate?.onDestroy()
        AppManager.fragmentDelegates?.remove(iFragment.hashCode())
        fragmentManager = null
        fragment = null
        iFragment = null
    }

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