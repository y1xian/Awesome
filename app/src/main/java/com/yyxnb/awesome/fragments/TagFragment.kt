package com.yyxnb.awesome.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.base.IFragment
import com.yyxnb.awesome.R
import com.yyxnb.common.AppConfig
import com.yyxnb.common.AppConfig.getInstance
import com.yyxnb.view.text.FlowlayoutTags
import com.yyxnb.view.text.FlowlayoutTags.OnTagClickListener
import kotlinx.android.synthetic.main.fragment_tag.*
import java.util.*

/**
 * Tag标签.
 */
@BindRes
class TagFragment : Fragment(), IFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag, container, false)
    }

    fun newInstance(): TagFragment? {
        val args = Bundle()
        val fragment = TagFragment()
        fragment.arguments = args
        return fragment
    }

    override fun initView(savedInstanceState: Bundle?) {
        val list: MutableList<String> = ArrayList()
        list.add("绿色足球鞋")
        list.add("白色棒球帽")
        list.add("黑色毛衣外套")
        list.add("褐色牛仔连衣裙")
        list.add("白色圆领衬衫")
        list.add("红色长袖连衣裙")
        refreshCategorys(mFlowlayoutTags, list)
        refreshCategorys(mFlowlayoutTags1, list)
        refreshCategorys(mFlowlayoutTags2, list)
        mFlowlayoutTags.setOnTagClickListener { tag: String? -> AppConfig.toast(tag.toString()) }
        mFlowlayoutTags1.setOnTagClickListener { tag: String? -> AppConfig.toast(tag.toString()) }
        mFlowlayoutTags2.setOnTagClickListener { tag: String? ->
            val tagList = mFlowlayoutTags2.getCheckedTagsTextsArrayList()
            val s = StringBuilder()
            for (i in tagList.indices) {
                s.append(tagList[i]).append(",")
            }
            AppConfig.toast(s.toString())
        }
    }

    fun refreshCategorys(flowlayoutTags: FlowlayoutTags?, list: List<String>?) {
        flowlayoutTags!!.removeAllViews()
        flowlayoutTags.setTags(list)
        flowlayoutTags.setTagsUncheckedColorAnimal(false)
    }
}