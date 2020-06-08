package com.yyxnb.awesome

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.arch.base.IActivity
import com.yyxnb.awesome.vm.TestViewModel
import com.yyxnb.common.AppConfig.log
import com.yyxnb.common.log.LogUtils

class TestActivity : AppCompatActivity(), IActivity {
    @BindViewModel
    var viewModel: TestViewModel? = null
    override fun initLayoutResId(): Int {
        return R.layout.activity_test
    }

    override fun initView(savedInstanceState: Bundle?) {
        log("1 initView")
    }

    override fun initViewData() {
//        startFragment(TitleFragment.newInstance());
        log("1 initViewData")
        viewModel!!.result.value = "11111111111111"
        viewModel!!.result.observe(this, Observer { s: String? -> LogUtils.e(s) })
    } //    @Override
    //    public int initLayoutResId() {
    //        return R.layout.activity_test;
    //    }
    //    @Override
    //    public void initView() {
    //        AppConfig.getInstance().log("2 initView");
    //    }
    //
    //    @Override
    //    public void initData() {
    //        AppConfig.getInstance().log("2 initData");
    //        LogUtils.list(ActivityManagerUtils.getInstance().getActivityList());
    //    }
}