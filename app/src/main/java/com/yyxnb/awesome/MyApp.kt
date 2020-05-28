package com.yyxnb.awesome

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.squareup.leakcanary.LeakCanary
import com.yyxnb.http.config.HttpConfig

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //网络
        initRxHttp()
        //ARouter的初始化
        initArouter()
        //SmartRefreshLayout的统一设置
        initSmartRefreshLayout()

//        ImageHelper.INSTANCE.init(new GlideImage());
//        HttpHelper.INSTANCE.init(new OkHttp()).setBaseUrl("http://www.mocky.io/");
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    private fun initRxHttp() {
//        RetrofitManager.INSTANCE
//                .init(this)
//                .setBaseUrl(BASE_URL)
//                .setHeaderPriorityEnable(true)
//                .putHeaderBaseUrl(URL_KEY_1, BASE_URL_MOCKY)
//                .setOkClient(mClient);

        HttpConfig.apply {
            baseUrl = ""
            retSuccess = "200"
            logOpen = true
        }

//        HttpConfig.retSuccess = "200"

    }


    /**
     * ARouter的初始化
     */
    private fun initArouter() {
        //测试环境
//        if (BuildConfig.OPEN_LOG) {
//            ARouter.openLog() // 打印日志
//            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
//        ARouter.init(this)
    }

    /**
     * SmartRefreshLayout的统一设置
     */
    private fun initSmartRefreshLayout() {
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

    companion object {
        //    private OkHttpClient mClient = new OkHttpConfig.Builder()
        //            //全局的请求头信息
        ////            .setHeaders(new HashMap<>())
        //            //开启缓存策略(默认false)
        //            //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
        //            //2、在没有网络的时候，去读缓存中的数据。
        //            .setCache(true)
        //            //全局持久话cookie,保存本地每次都会携带在header中（默认false）
        //            .setSaveCookie(true)
        //            //可以添加自己的拦截器(比如使用自己熟悉三方的缓存库等等)
        ////            .setAddInterceptor(new RetryInterceptor.Builder()
        ////                    .executionCount(1).retryInterval(888)
        ////                    .build())
        //            //全局ssl证书认证
        //            //1、信任所有证书,不安全有风险（默认信任所有证书）
        //            .setSslSocketFactory()
        //            //2、使用预埋证书，校验服务端证书（自签名证书）
        ////            .setSslSocketFactory(cerInputStream)
        //            //3、使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //            //.setSslSocketFactory(bksInputStream,"123456",cerInputStream)
        //            //全局超时配置
        //            .setTimeout(8)
        //            //全局是否打开请求log日志
        //            .setLogEnable(true)
        //            .build();
        //static 代码段可以防止内存泄露
        init {



        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)
        //        BLAutoInjectController.setEnableAutoInject(false);
    }
}