package com.yyxnb.awesome.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.arch.base.IFragment
import com.yyxnb.awesome.R
import com.yyxnb.awesome.vm.TestViewModel
import com.yyxnb.common.log.LogUtils
import com.yyxnb.ktx.log

/**
 * 实现IFragment.
 */
@BindRes(subPage = true)
class TestFragment : Fragment(), IFragment {

    @BindViewModel
    lateinit var viewModel: TestViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        log("TestFragment initView")
    }

    override fun initObservable() {
//        viewModel.result.postValue("333333")
        viewModel.result.observe(this, Observer { s: String? -> LogUtils.e("test : $s") })
    }

    override fun initViewData() {
        log("initViewData test: " + hashCode())
    }

    override fun onVisible() {
        log("onVisible test: " + hashCode())
    }

    override fun onInVisible() {
        log("onInVisible test: " + hashCode())
    }

    // vp下 要自实现setUserVisibleHint
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        getBaseDelegate().setUserVisibleHint(isVisibleToUser)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        getBaseDelegate().onHiddenChanged(hidden)
    }
}