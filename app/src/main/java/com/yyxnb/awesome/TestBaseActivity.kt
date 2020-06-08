package com.yyxnb.awesome

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.yyxnb.arch.annotations.BarStyle
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.arch.base.BaseActivity
import com.yyxnb.awesome.vm.TestViewModel
import com.yyxnb.common.log.LogUtils.e

@BindRes(layoutRes = R.layout.activity_test_base, fitsSystemWindows = true, statusBarColor = R.color.black_40, statusBarStyle = BarStyle.LightContent)
class TestBaseActivity : BaseActivity() {
    @BindViewModel
    var viewModel: TestViewModel? = null
    override fun initView(savedInstanceState: Bundle?) {
        viewModel!!.result.value = "222222222222"
        viewModel!!.result.observe(this, Observer { s: String? -> })

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.mFrameLayout, new TestFragment(), "ggg")
//                .commitAllowingStateLoss();
    }

    override fun initViewData() {
        e(":initViewData")
    }
}