package com.yyxnb.awesome.adapter

import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.ItemDelegate
import com.yyxnb.adapter.ItemDiffCallback
import com.yyxnb.adapter.MultiItemTypePagedAdapter
import com.yyxnb.awesome.R
import com.yyxnb.awesome.bean.MainBean

class MainListAdapter : MultiItemTypePagedAdapter<MainBean>(ItemDiffCallback()) {
    init {
        addItemDelegate(object : ItemDelegate<MainBean> {
            override fun layoutId(): Int {
                return R.layout.item_main_title_layout
            }

            override fun isThisType(item: MainBean, position: Int): Boolean {
                return item.type == 1
            }

            override fun convert(holder: BaseViewHolder, mainBean: MainBean, position: Int) {
                holder.setText(R.id.tvText, mainBean.title)
            }
        })
        addItemDelegate(object : ItemDelegate<MainBean> {
            override fun layoutId(): Int {
                return R.layout.item_main_list_layout
            }

            override fun isThisType(item: MainBean, position: Int): Boolean {
                return item.type == 0
            }

            override fun convert(holder: BaseViewHolder, mainBean: MainBean, position: Int) {
                val url = "http://img0.imgtn.bdimg.com/it/u=4073821464,3431246218&fm=26&gp=0.jpg"
                holder.setText(R.id.tvText, mainBean.title)
            }
        })
    }
}