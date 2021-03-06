package com.yyxnb.awesome.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.awesome.R
import com.yyxnb.awesome.base.BaseFragment
import com.yyxnb.awesome.vm.TestViewModel
import com.yyxnb.common.log.LogUtils.e
import com.yyxnb.ktx.log

/**
 * 继承BaseFragment.
 */
@BindRes(layoutRes = R.layout.fragment_test_base, subPage = true)
class TestBaseFragment : BaseFragment() {

    @BindViewModel
    lateinit var viewModel: TestViewModel

    override fun initView(savedInstanceState: Bundle?) {}

    override fun initObservable() {
        viewModel.result.observe(this, Observer { s: String? -> e("testBase : $s") })
    }

    override fun initViewData() {
        log("initViewData testbase: " + hashCode())
    }

    override fun onVisible() {
        log("onVisible testbase: " + hashCode())
    }

    override fun onInVisible() {
        log("onInVisible testbase: " + hashCode())
    }
}