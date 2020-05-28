package com.yyxnb.awesome.adapter;

import com.yyxnb.adapter.BaseAdapter;
import com.yyxnb.adapter.BaseViewHolder;
import com.yyxnb.awesome.R;
import com.yyxnb.awesome.bean.TikTokBean;

public class NetWorkListAdapter extends BaseAdapter<TikTokBean> {


    public NetWorkListAdapter() {
        super(R.layout.item_test_list_layout);
    }

    @Override
    protected void bind(BaseViewHolder holder, TikTokBean bean, int position) {
        holder.setText(R.id.tvText, " --- 第 " + bean.id + " 条 ------- " + bean.title);

        addChildClickViewIds(holder, R.id.btnAdd);
        addChildLongClickViewIds(holder, R.id.btnAdd);
    }
}
