package com.yyxnb.awesome

import com.yyxnb.awesome.base.BaseFragment
import com.yyxnb.awesome.base.ContainerActivity
import com.yyxnb.awesome.fragments.MainFragment

class MainActivity : ContainerActivity() {
    //    @Override
    //    protected void onCreate(Bundle savedInstanceState) {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_main);
    //    }
    override fun initBaseFragment(): BaseFragment {
        return MainFragment()
    }
}