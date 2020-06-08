package com.yyxnb.arch.delegate

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.LruCache
import android.view.View
import com.yyxnb.arch.base.IFragment
import com.yyxnb.arch.utils.FragmentManagerUtils.killFragment
import com.yyxnb.arch.utils.FragmentManagerUtils.pushFragment

object FragmentLifecycle : FragmentManager.FragmentLifecycleCallbacks() {

    private val cache = LruCache<String, IFragmentDelegate>(100)

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        pushFragment(f)
        if (f !is IFragment) {
            return
        }

        val fragmentDelegate = fetchFragmentDelegate(f, fm)

        fragmentDelegate.onAttached(context)
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        fetchFragmentDelegateFromCache(f)?.onCreated(savedInstanceState)
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        fetchFragmentDelegateFromCache(f)?.onViewCreated(v, savedInstanceState)
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        fetchFragmentDelegateFromCache(f)?.onActivityCreated(savedInstanceState)
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegateFromCache(f)?.onStarted()
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegateFromCache(f)?.onResumed()
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegateFromCache(f)?.onPaused()
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegateFromCache(f)?.onStopped()
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        fetchFragmentDelegateFromCache(f)?.onSaveInstanceState(outState)
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegateFromCache(f)?.onViewDestroyed()
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        killFragment(f)
        fetchFragmentDelegateFromCache(f)?.onDestroyed()
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        fetchFragmentDelegateFromCache(f)?.onDetached()
    }

    private fun fetchFragmentDelegate(fragment: Fragment, manager: FragmentManager): IFragmentDelegate {
        return fetchFragmentDelegateFromCache(fragment)
                ?: newDelegate(manager, fragment).apply { cache.put(getKey(fragment), this) }
    }

    private fun fetchFragmentDelegateFromCache(fragment: Fragment): IFragmentDelegate? {
        if (fragment !is IFragment) {
            return null
        }

        return cache[getKey(fragment)]
    }

    private fun newDelegate(manager: FragmentManager, fragment: Fragment): IFragmentDelegate {
        return FragmentDelegateImpl(manager, fragment)
    }

    private fun getKey(f: Fragment) = f.javaClass.name + f.hashCode()

}