package com.yyxnb.awesome

import com.yyxnb.arch.ContainerActivity
import com.yyxnb.arch.base.BaseFragment
import com.yyxnb.awesome.fragments.HomeFragment

class MainActivity : ContainerActivity() {
    //    @Override
    //    protected void onCreate(Bundle savedInstanceState) {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_main);
    //    }
    override fun initBaseFragment(): BaseFragment {
        return HomeFragment()
    }
}