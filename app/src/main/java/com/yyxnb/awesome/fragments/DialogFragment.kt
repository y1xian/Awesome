package com.yyxnb.awesome.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yyxnb.adapter.BaseViewHolder
import com.yyxnb.adapter.ItemDecoration
import com.yyxnb.adapter.MultiItemTypeAdapter
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.base.IFragment
import com.yyxnb.awesome.R
import com.yyxnb.awesome.adapter.StringListAdapter
import com.yyxnb.awesome.data.DataConfig
import com.yyxnb.awesome.popup.CommentBottomPopup
import com.yyxnb.awesome.popup.CustomFullScreenPopup
import com.yyxnb.awesome.popup.RegisterBottomPopup
import com.yyxnb.awesome.popup.VpBottomPopup
import com.yyxnb.common.AppConfig
import com.yyxnb.common.interfaces.OnCancelListener
import com.yyxnb.common.interfaces.OnConfirmListener
import com.yyxnb.common.interfaces.OnSelectListener
import com.yyxnb.view.popup.Popup
import kotlinx.android.synthetic.main.fragment_dialog.*

/**
 * 对话框.
 */
@BindRes
class DialogFragment : Fragment(), IFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    private val mAdapter: StringListAdapter by lazy { StringListAdapter() }

    override fun initView(savedInstanceState: Bundle?) {

        mTitleBar.setBackListener { v: View? -> getBaseDelegate().finish() }

        val decoration = ItemDecoration(context)
        decoration.isOnlySetItemOffsetsButNoDraw = true
        decoration.isDrawBorderTopAndBottom = true
        decoration.isDrawBorderLeftAndRight = true
        decoration.setDividerHeight(10)
        decoration.setDividerWidth(20)
        val manager = GridLayoutManager(context, 2)
        mRecyclerView.layoutManager = manager
        mRecyclerView.addItemDecoration(decoration)
        mRecyclerView.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : MultiItemTypeAdapter.SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: BaseViewHolder, position: Int) {
                super.onItemClick(view, holder, position)
                when (position) {
                    0 -> Popup.Builder(context).asLoading("带标题")
                            .show()
                    1 -> Popup.Builder(context).asConfirm("标题", "内容", object : OnConfirmListener {
                        override fun onConfirm() {
                            AppConfig.toast("确认")
                        }
                    }, object : OnCancelListener {
                        override fun onCancel() {
                            AppConfig.toast("取消")
                        }
                    }).show()
                    2 -> Popup.Builder(context).asInputConfirm("标题", "内容", "", "请输入", { text: String -> AppConfig.toast("确认 ：$text") }, object : OnCancelListener {
                        override fun onCancel() {
                            AppConfig.toast("取消")
                        }
                    }).show()
                    3 -> Popup.Builder(context).asCenterList("标题", arrayOf("序列1", "序列2", "序列3"), object : OnSelectListener {
                        override fun onClick(v: View?, position1: Int, text: String?) {
                            AppConfig.toast("选中 $position1，$text")
                        }
                    }).show()
                    4 -> Popup.Builder(context).asCenterList("标题", arrayOf("序列1", "序列2", "序列3"), intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher), 1,
                            R.mipmap.ic_launcher, object : OnSelectListener {
                        override fun onClick(v: View?, position1: Int, text: String?) {
                            AppConfig.toast("选中 $position1，$text")
                        }
                    }).show()
                    5 -> Popup.Builder(context).asBottomList("标题", arrayOf("序列1", "序列2", "序列3"), object : OnSelectListener {
                        override fun onClick(v: View?, position1: Int, text: String?) {
                            AppConfig.toast("选中 $position1，$text")
                        }
                    }).show()
                    6 -> Popup.Builder(context).asBottomList("标题", arrayOf("序列1", "序列2", "序列3"), intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher), 1,
                            R.mipmap.ic_launcher, object : OnSelectListener {
                        override fun onClick(v: View?, position1: Int, text: String?) {
                            AppConfig.toast("选中 $position1，$text")
                        }
                    }).show()
                    7 -> Popup.Builder(context)
                            .hasStatusBarShadow(false)
                            .autoOpenSoftInput(false)
                            .asCustom(CustomFullScreenPopup(context!!))
                            .show()
                    8 -> Popup.Builder(context)
                            .moveUpToKeyboard(false)
                            .asCustom(RegisterBottomPopup(context!!))
                            .show()
                    9 -> Popup.Builder(context)
                            .moveUpToKeyboard(false)
                            .asCustom(CommentBottomPopup(context!!))
                            .show()
                    10 -> Popup.Builder(context)
                            .moveUpToKeyboard(false)
                            .asCustom(VpBottomPopup(context!!))
                            .show()
                    else -> {
                    }
                }
            }

            override fun onItemLongClick(view: View, holder: BaseViewHolder, position: Int): Boolean {
                when (position) {
                    0 -> Popup.Builder(context).asLoading("带标题")
                            .bindLayout(R.layout.popup_center_impl_loading)
                            .show()
                    1 -> Popup.Builder(context).asConfirm("标题", "内容", object : OnConfirmListener {
                        override fun onConfirm() {
                            AppConfig.toast("确认")
                        }
                    }, object : OnCancelListener {
                        override fun onCancel() {
                            AppConfig.toast("取消")
                        }
                    }).bindLayout(R.layout.popup_tip_confirm).show()
                    2 -> Popup.Builder(context).asInputConfirm("标题", "内容", "", "请输入", { text: String -> AppConfig.toast("确认 ：$text") }, object : OnCancelListener {
                        override fun onCancel() {
                            AppConfig.toast("取消")
                        }
                    }).bindLayout(R.layout.popup_tip_confirm).show()
                    3 -> {
                    }
                    else -> {
                    }
                }
                return true
            }
        })
    }

    override fun initViewData() {
        mAdapter.setDataItems(DataConfig.getDialogList())
    }
}