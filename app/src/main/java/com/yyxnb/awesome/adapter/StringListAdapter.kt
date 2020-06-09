package com.yyxnb.awesome.adapter

import com.yyxnb.adapter.BaseAdapter
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.awesome.R

class StringListAdapter : BaseAdapter<String>(R.layout.item_string_list_layout) {

    override fun bind(holder: BaseViewHolder, s: String, position: Int) {
        holder.setText(R.id.tvText, s)
    }

}