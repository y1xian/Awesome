package com.yyxnb.arch

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import com.yyxnb.arch.base.BaseActivity
import com.yyxnb.arch.base.BaseFragment
import com.yyxnb.arch.common.ArchConfig
import com.yyxnb.common.AppConfig

/**
 * Description: 盛装Fragment的一个容器(代理)Activity
 * 普通界面只需要编写Fragment,使用此Activity盛装,这样就不需要每个界面都在AndroidManifest中注册一遍
 *
 * @author : yyx
 * @date ：2018/6/9
 */
open class ContainerActivity : BaseActivity() {

    override fun initLayoutResId(): Int {
        return R.layout.base_nav_content
    }

    open fun initBaseFragment(): BaseFragment? {
        return null
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun initView(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun initViewData() {
        try {
            val intent = intent ?: throw RuntimeException("you must provide a page info to display")
            if (initBaseFragment() != null) {
                if (intent.getBundleExtra(ArchConfig.BUNDLE) != null) {
                    initBaseFragment()!!.arguments = intent.getBundleExtra(ArchConfig.BUNDLE)
                }
                setRootFragment(initBaseFragment()!!, R.id.fragmentContent)
                return
            }
            val fragmentName = intent.getStringExtra(ArchConfig.FRAGMENT)
            require(!fragmentName.isEmpty()) { "can not find page fragmentName" }
            val fragmentClass = Class.forName(fragmentName)
            val fragment = fragmentClass.newInstance() as BaseFragment
            if (intent.getBundleExtra(ArchConfig.BUNDLE) != null) {
                fragment.arguments = intent.getBundleExtra(ArchConfig.BUNDLE)
            }
            setRootFragment(fragment, R.id.fragmentContent)
        } catch (e: Exception) {
            e.printStackTrace()
            AppConfig.log(e.message.toString())
        }
    }
}