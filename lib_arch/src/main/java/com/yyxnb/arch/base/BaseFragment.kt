package com.yyxnb.arch.base

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyxnb.arch.delegate.FragmentDelegate
import java.lang.ref.WeakReference
import java.util.*

abstract class BaseFragment : Fragment(), IFragment {

    protected val TAG = javaClass.canonicalName
    protected var mActivity: WeakReference<AppCompatActivity>? = null
    protected var mContext: WeakReference<Context>? = null
    protected var mRootView: View? = null
    private val mFragmentDelegate by lazy { FragmentDelegate(this) }
    private val java8Observer: Java8Observer

    init {
        java8Observer = Java8Observer(TAG!!)
        lifecycle.addObserver(java8Observer)
    }

    override fun getContext(): Context? {
        return mContext!!.get()
    }

    override fun getBaseDelegate(): FragmentDelegate? {
        return mFragmentDelegate
    }

    fun <B : ViewDataBinding> getBinding(): B? {
        DataBindingUtil.bind<B>(mRootView!!);
        return DataBindingUtil.getBinding(mRootView!!)
    }

    fun sceneId(): String {
        return UUID.randomUUID().toString()
    }

    fun hasId(): Int {
        assert(TAG != null)
        return Math.abs(TAG.hashCode())
    }

    fun initArguments(): Bundle {
        return mFragmentDelegate.initArguments()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                mContext = WeakReference(context)
                mActivity = WeakReference(mContext!!.get() as AppCompatActivity)
                mFragmentDelegate.onAttach(mActivity!!.get())
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentDelegate.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mRootView = mFragmentDelegate.onCreateView(inflater, container, savedInstanceState)
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mFragmentDelegate.onActivityCreated(savedInstanceState)
        //当设备旋转时，fragment会随托管activity一起销毁并重建。
//        retainInstance = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mFragmentDelegate.setUserVisibleHint(isVisibleToUser)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mFragmentDelegate.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mFragmentDelegate.onConfigurationChanged(newConfig)
    }

    override fun onResume() {
        super.onResume()
        mFragmentDelegate.onResume()
    }

    override fun onPause() {
        super.onPause()
        mFragmentDelegate.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mFragmentDelegate.onDestroy()
//        mFragmentDelegate = null
        result = null
        mContext!!.clear()
        mContext = null
        mRootView = null
        mActivity!!.clear()
        mActivity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mFragmentDelegate.onDestroyView()
        lifecycle.removeObserver(java8Observer)
    }

    override fun initViewData() {
//        mFragmentDelegate.initDeclaredFields()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> findViewById(@IdRes resId: Int): T {
        return mRootView?.findViewById<View>(resId) as T
    }

    /**
     * 返回.
     */
    fun finish() {
        mActivity!!.get()!!.onBackPressed()
    }

    fun <T : BaseFragment> startFragment(targetFragment: T) {
        startFragment(targetFragment, 0)
    }

    fun <T : BaseFragment> startFragment(targetFragment: T, requestCode: Int) {
        mFragmentDelegate.startFragment(targetFragment, requestCode)
    }

    @JvmField
    var requestCode = 0
    @JvmField
    var resultCode = 0
    @JvmField
    var result: Intent? = null
    fun setResult(resultCode: Int, result: Intent?) {
        this.resultCode = resultCode
        this.result = result
    }

}