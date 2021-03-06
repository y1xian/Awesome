package com.yyxnb.awesome

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.github.anzewei.parallaxbacklayout.ParallaxHelper
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.squareup.leakcanary.LeakCanary
import me.jessyan.autosize.AutoSizeConfig

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // 布局
        AutoSizeConfig.getInstance().isCustomFragment = true
        // 侧滑监听
        registerActivityLifecycleCallbacks(ParallaxHelper.getInstance())

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    /**
     * SmartRefreshLayout的统一设置
     */
    init {
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer { context, layout -> //开始设置全局的基本参数（可以被下面的DefaultRefreshHeaderCreator覆盖）
            layout.setReboundDuration(1000)
            layout.setFooterHeight(100f)
            //是否启用越界回弹
            layout.setEnableOverScrollBounce(true)
            //是否启用越界拖动（仿苹果效果）1.0.4
            layout.setEnableOverScrollDrag(true)
            //是否在加载的时候禁止列表的操作
            layout.setDisableContentWhenLoading(false)
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
        }

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.black) //全局设置主题颜色
            ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            //                return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)
        //        BLAutoInjectController.setEnableAutoInject(false);
    }
}