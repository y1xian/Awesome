package com.yyxnb.awesome.fragments

import android.arch.paging.PagedListAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.MultiItemTypePagedAdapter
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.awesome.TestActivity
import com.yyxnb.awesome.TestBaseActivity
import com.yyxnb.awesome.adapter.MainListAdapter
import com.yyxnb.awesome.bean.MainBean
import com.yyxnb.awesome.vm.MainViewModel

/**
 * 主页.
 */
@BindRes
class MainFragment : AbsListFragment<MainBean, MainViewModel>() {

    private val mAdapter: MainListAdapter by lazy { MainListAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mRefreshLayout.setEnableRefresh(false).setEnableLoadMore(false)
        mAdapter.setOnItemClickListener(object : MultiItemTypePagedAdapter.SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: BaseViewHolder, position: Int) {
                super.onItemClick(view, holder, position)
                setMenu(mAdapter.getData().get(position)!!.id)
            }
        })
        val manager = GridLayoutManager(context, 2)
        mAdapter.setSpanSizeLookup(MultiItemTypePagedAdapter.SpanSizeLookup { _: GridLayoutManager?, position: Int ->
            if (mAdapter.getData().get(position)?.type == 1) {
                return@SpanSizeLookup 2
            }
            1
        })
        mRecyclerView.layoutManager = manager
        decoration.setDividerWidth(5)
        decoration.setDividerHeight(5)
        //        decoration.setDrawBorderTopAndBottom(true);
//        decoration.setDrawBorderLeftAndRight(true);
        mRecyclerView.adapter = adapter
        mAdapter.addHeaderView(view("头部啊"))
        mAdapter.addFooterView(view("底部啊"))
    }

    private fun view(s: String): TextView {
        val textView = TextView(context)
        val layoutParams: ViewGroup.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.height = 200
        textView.layoutParams = layoutParams
        textView.gravity = Gravity.CENTER
        textView.text = s
        return textView
    }

    private fun setMenu(position: Int) {
        when (position) {
            11 -> startActivity(Intent(context, TestBaseActivity::class.java))
            12 -> startActivity(Intent(context, TestActivity::class.java))
            21 -> startFragment(TestBaseFragment())
            22 -> startFragment(TestFragment())
            23 -> startFragment(VpMainFragment())
            24 -> startFragment(BottomFragment())
            31 -> startFragment(NetWorkFragment())
            else -> {
            }
        }
    }

    override fun getAdapter(): PagedListAdapter<*, *> {
        return mAdapter
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {}

    override fun onRefresh(refreshLayout: RefreshLayout) {}
}