package com.yyxnb.awesome.fragments

import android.os.Bundle
import android.view.View
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.base.BaseFragment
import com.yyxnb.awesome.R
import kotlinx.android.synthetic.main.fragment_title.*

/**
 * 标题栏.
 */
@BindRes(layoutRes = R.layout.fragment_title)
class TitleFragment : BaseFragment() {

//    private var mTitleBar: TitleBar? = null

    override fun initView(savedInstanceState: Bundle?) {
//        mTitleBar = findViewById(R.id.mTitleBar)

//        mTitleBar.setBackgroundResource(R.drawable.shape_gradient_bg);
        mTitleBar.showCenterProgress()

//        mTitleBar.setClickListener((v, action) -> {
//            switch (action) {
//                case ACTION_LEFT_BUTTON:
//                    finish();
//                    break;
//                default:
//                    break;
//            }
//        });

        mTitleBar.setBackListener { v: View? -> finish() }
    }

    override fun initViewData() {
        super.initViewData()

    }

}