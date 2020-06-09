package com.yyxnb.arch

import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.support.multidex.MultiDex
import com.github.anzewei.parallaxbacklayout.ParallaxHelper
import com.jeremyliao.liveeventbus.LiveEventBus
import com.yyxnb.arch.delegate.ActivityLifecycle
import com.yyxnb.common.AppConfig
import com.yyxnb.common.BuildConfig
import com.yyxnb.common.log.LogUtils
import me.jessyan.autosize.AutoSizeConfig

/**
 * 使用ContentProvider初始化三方库
 */
class LibraryInitializer : ContentProvider() {
    override fun onCreate(): Boolean {
        // 初始化
        val context = AppConfig.getInstance().context
        AutoSizeConfig.getInstance().isCustomFragment = true

        //突破65535的限制
        MultiDex.install(context)

        //系统会在每个 Activity 执行完对应的生命周期后都调用这个实现类中对应的方法
        AppConfig.getInstance().app.registerActivityLifecycleCallbacks(ActivityLifecycle)
        // 侧滑监听
        AppConfig.getInstance().app.registerActivityLifecycleCallbacks(ParallaxHelper.getInstance())
        // 应用监听
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifeObserver())

        LiveEventBus
                .config()
                .enableLogger(BuildConfig.DEBUG)

        //设置全局tag
        LogUtils.init()
                .setTag("---Awesome---")
                //是否显示日志，默认true，发布时最好关闭
                .setShowThreadInfo(true)
                .setDebug(AppConfig.getInstance().isDebug)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}