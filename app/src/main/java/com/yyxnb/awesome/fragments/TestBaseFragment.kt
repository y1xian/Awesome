package com.yyxnb.awesome.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.arch.base.BaseFragment
import com.yyxnb.awesome.R
import com.yyxnb.awesome.vm.TestViewModel
import com.yyxnb.common.AppConfig.log
import com.yyxnb.common.log.LogUtils.e

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
        log("initViewData : " + hashCode())
    }

    override fun onVisible() {
        log("onVisible : " + hashCode())
    }

    override fun onInVisible() {
        log("onInVisible : " + hashCode())
    }
}