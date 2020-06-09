package com.yyxnb.awesome.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.MultiItemTypeAdapter
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.annotations.BindViewModel
import com.yyxnb.arch.base.BaseFragment
import com.yyxnb.awesome.R
import com.yyxnb.awesome.adapter.NetWorkListAdapter
import com.yyxnb.awesome.bean.StateData
import com.yyxnb.awesome.bean.TikTokBean
import com.yyxnb.awesome.vm.NetWorkViewModel
import com.yyxnb.common.AppConfig.log
import kotlinx.android.synthetic.main.fragment_net_work.*

/**
 * 网络列表.
 */
@BindRes(layoutRes = R.layout.fragment_net_work, subPage = true)
class NetWorkFragment : BaseFragment() {

    @BindViewModel
    lateinit var mViewModel: NetWorkViewModel

    private val mAdapter by lazy { NetWorkListAdapter() }
    private val page = 1
    override fun initView(savedInstanceState: Bundle?) {

        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView!!.adapter = mAdapter

//        View view = LayoutInflater.from(mContext).inflate(R.layout._loading_layout_empty, (ViewGroup) getMRootView(), false);
//        mAdapter.setEmptyView(view);
//        mAdapter.setEmptyView(R.layout._loading_layout_empty);
//        mAdapter.addFootView(view);

        mRefreshLayout!!.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
//                mAdapter.setDataItems(null);
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
//                mViewModel.reqTeam();
            }
        })
        mAdapter.setOnItemClickListener(object : MultiItemTypeAdapter.SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: BaseViewHolder, position: Int) {
                super.onItemClick(view, holder, position)
            }

            override fun onItemChildClick(view: View, holder: BaseViewHolder, position: Int) {
                super.onItemChildClick(view, holder, position)
                if (view.id == R.id.mLinearLayout) {

                }
            }
        })
    }

    override fun initViewData() {
        super.initViewData()
        log(" initViewData ")

        mViewModel.videoList("123")

        mViewModel.result.observe(this, Observer { t: StateData<TikTokBean>? ->
//                    page++;
            log(" SUCCESS   1")
            mRefreshLayout!!.finishRefresh()
            if (t != null && t.list != null) {
                log(" SUCCESS   " + t.list!!.size)
                mAdapter!!.setDataItems(t.list)
            }
        })
    }

}