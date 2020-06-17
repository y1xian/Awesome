package com.yyxnb.awesome.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.base.BaseFragment
import com.yyxnb.awesome.R
import kotlinx.android.synthetic.main.fragment_bottom.*
import java.util.*

/**
 * 底部show/hide
 */
@BindRes(layoutRes = R.layout.fragment_bottom)
class BottomFragment : BaseFragment(), View.OnClickListener {

    private var manager: FragmentManager? = null
    private var fragments: MutableList<Fragment>? = null
    private var current = -1

    override fun initView(savedInstanceState: Bundle?) {
        rb_1.setOnClickListener(this)
        rb_2.setOnClickListener(this)
        rb_3.setOnClickListener(this)
    }

    override fun initViewData() {
        manager = childFragmentManager
        if (fragments == null) {
            fragments = ArrayList()
            fragments?.add(VpMainFragment())
            fragments?.add(TestFragment())
            fragments?.add(TestBaseFragment())
        }
        showFragment(0)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rb_1 -> showFragment(0)
            R.id.rb_2 -> showFragment(1)
            R.id.rb_3 -> showFragment(2)
            else -> {
            }
        }
    }

    private fun showFragment(index: Int) {
        if (current == index) {
            return
        }
        val transaction = manager!!.beginTransaction()
        if (!fragments!![index].isAdded) {
            transaction.add(R.id.mFrameLayout, fragments!![index])
        }
        if (current != -1) {
            transaction.hide(fragments!![current])
        }
        current = index
        transaction.show(fragments!![current]).commit()
    }
}