package com.yyxnb.awesome.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.adapter.MultiItemTypeAdapter;
import com.yyxnb.arch.annotations.BindFragment;
import com.yyxnb.arch.annotations.BindViewModel;
import com.yyxnb.arch.base.BaseFragment;
import com.yyxnb.awesome.R;
import com.yyxnb.awesome.adapter.NetWorkListAdapter;
import com.yyxnb.awesome.vm.NetWorkViewModel;
import com.yyxnb.common.AppConfig;


/**
 * 网络列表.
 */
@BindFragment(layoutRes = R.layout.fragment_net_work)
public class NetWorkFragment extends BaseFragment/*VM<NetWorkViewModel>*/ {

    @BindViewModel
    NetWorkViewModel mViewModel;
//    @BindViewModel
//    MsgViewModel msgViewModel;

    private NetWorkListAdapter mAdapter;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private int page = 1;

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mRefreshLayout = findViewById(R.id.mRefreshLayout);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new NetWorkListAdapter();
//        mAdapter = new RecyclerAdapter();

        mRecyclerView.setAdapter(mAdapter);

//        View view = LayoutInflater.from(mContext).inflate(R.layout._loading_layout_empty, (ViewGroup) getMRootView(), false);
//        mAdapter.setEmptyView(view);
//        mAdapter.setEmptyView(R.layout._loading_layout_empty);
//        mAdapter.addFootView(view);


        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                mAdapter.setDataItems(null);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mViewModel.reqTeam();
            }
        });

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() {
//            @Override
////            public void onItemClick(@NotNull View view, @NotNull RecyclerView.ViewHolder holder, int position) {
////                ToastUtils.INSTANCE.normal("" + position);
////            }


            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                super.onItemClick(view, holder, position);

            }

            @Override
            public void onItemChildClick(View view, BaseViewHolder holder, int position) {
                super.onItemChildClick(view, holder, position);
                if (view.getId() == R.id.mLinearLayout) {
//                    msgViewModel.reqToast("第 " + position);
                }
            }
        });


    }

    @Override
    public void initViewData() {
        super.initViewData();

        AppConfig.INSTANCE.log(" initViewData ");

//        mViewModel.reqTeam2();

//        LogUtils.INSTANCE.e(CacheManager.cacheSize() + " 条");

//        mViewModel.getTestList().observe(this, t -> {
//            switch (t.status) {
//                case SUCCESS:
////                    page++;
//                    mRefreshLayout.finishRefresh();
//                    if (t.data != null) {
//                        AppConfig.getInstance().log(" success   " + (t.data.getData().list.size()));
////                        LogUtils.INSTANCE.list(t.data.getData());
//                        mAdapter.setDataItems(t.data.getData().list);
//                    }
//                    break;
//                case ERROR:
//                    AppConfig.getInstance().log(" ERROR ");
//                    break;
//                case LOADING:
////                    if (t.data != null) {
////                        AppConfig.getInstance().log(" loading   " + (t.data.getList().size() > 0));
////                        mAdapter.setDataItems(t.data.getList());
////                    }
//                    break;
//            }
//        });
//        mViewModel.getTestList().observe(this, t -> {
//            switch (t.status) {
//                case SUCCESS:
////                    page++;
//                    mRefreshLayout.finishRefresh();
//                    if (t.data != null) {
//                        AppConfig.getInstance().log(" SUCCESS   " + (t.data.data.list.size()));
////                        LogUtils.INSTANCE.list(t.data.getData());
//                        mAdapter.setDataItems(t.data.data.list);
//                    }
//                    break;
//                case ERROR:
//                    AppConfig.getInstance().log(" ERROR ");
//                    break;
//                case LOADING:
//                    AppConfig.getInstance().log(" LOADING ");
////                    if (t.data != null) {
////                        AppConfig.getInstance().log(" loading   " + (t.data.getData().list.size() > 0));
////                        mAdapter.setDataItems(t.data.getData().list);
////                    }
//                    break;
//            }
//        });

        mViewModel.videoList("123");

        mViewModel.getResult().observe(this, t -> {
//                    page++;
            AppConfig.INSTANCE.log(" SUCCESS   1");
            mRefreshLayout.finishRefresh();
            if (t != null && t.list != null) {
                AppConfig.INSTANCE.log(" SUCCESS   " + (t.list.size()));
                //                        LogUtils.INSTANCE.list(t.data.getData());
                mAdapter.setDataItems(t.list);
            }
        });

    }

    public static NetWorkFragment newInstance() {

        Bundle args = new Bundle();

        NetWorkFragment fragment = new NetWorkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
//        CacheManager.deleteKey("getTestList");
        super.onDestroy();
    }
}
