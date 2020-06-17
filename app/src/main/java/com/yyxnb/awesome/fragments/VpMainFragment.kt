package com.yyxnb.awesome.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.yyxnb.arch.annotations.BindRes
import com.yyxnb.arch.base.BaseFragment
import com.yyxnb.awesome.R
import com.yyxnb.awesome.widget.BaseFragmentPagerAdapter
import com.yyxnb.common.DpUtils.dp2px
import kotlinx.android.synthetic.main.fragment_vp_main.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import java.util.*

/**
 * viewPager 切换.
 */
@BindRes(layoutRes = R.layout.fragment_vp_main, subPage = true)
class VpMainFragment : BaseFragment() {

    private val titles = arrayOf("1111", "2222", "3333")
    private var fragments: MutableList<Fragment>? = null

    override fun initView(savedInstanceState: Bundle?) {
        if (fragments == null) {
            fragments = arrayListOf()
            fragments?.add(TestFragment())
            fragments?.add(TestBaseFragment())
            fragments?.add(BottomFragment())
        }
        val commonNavigator = CommonNavigator(context)
        //ture 即标题平分屏幕宽度的模式
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titles.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.GRAY
                colorTransitionPagerTitleView.selectedColor = Color.BLACK
                colorTransitionPagerTitleView.text = titles[index]
                colorTransitionPagerTitleView.setOnClickListener { view: View? -> mViewPager.currentItem = index }
                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                //设置宽度
//                indicator.setLineWidth(DpUtils.dp2px(mContext,30));
                //设置高度
                indicator.lineHeight = dp2px(getContext()!!, 5f).toFloat()
                //设置颜色
                indicator.setColors(Color.parseColor("#FF9241"))
                //设置圆角
                indicator.roundRadius = 5f
                //设置模式
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                return indicator
            }
        }
        mIndicator.navigator = commonNavigator
        mViewPager.offscreenPageLimit = titles.size - 1
        mViewPager.adapter = BaseFragmentPagerAdapter(childFragmentManager, fragments, Arrays.asList(*titles))
        //与ViewPagger联动
        ViewPagerHelper.bind(mIndicator, mViewPager)
    }

}