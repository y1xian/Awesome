package com.yyxnb.common

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.yyxnb.common.log.LogUtils
import java.io.Serializable
import java.lang.ref.WeakReference
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType

object AppConfig : Serializable {

    private var app: WeakReference<Application>? = null

    fun getApp(): Application {
        if (app == null) {
            synchronized(AppConfig::class.java) {
                if (app == null) {
                    app = WeakReference<Application>(AppGlobals.application)
                }
            }
        }
        return app?.get()!!
    }

    /**
     * 获取ApplicationContext
     */
    private var context: WeakReference<Context>? = null

    fun getContext(): Context {
        if (context == null) {
            synchronized(AppConfig::class.java) {
                if (context == null) {
                    context = WeakReference<Context>(getApp().applicationContext)
                }
            }
        }
        return context?.get()!!
    }

    fun toast(s: String) {
        ToastUtils.normal(s)
    }

    fun log(tag: String, s: String) {
        if (isDebug) {
            LogUtils.w(s, tag)
        }
    }

    fun log(s: String) {
        if (isDebug) {
            log("------AppConfig------", s)
        }
    }

    /**
     * 判断当前应用是否是debug状态
     */
    val isDebug: Boolean
        get() = try {
            val info = getContext().applicationInfo
            info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (e: Exception) {
            false
        }

    /**
     * 返回实例的泛型类型
     */
    fun <T> getInstance(t: Any?, i: Int): T? {
        if (t != null) {
            try {
                return (t.javaClass.genericSuperclass as ParameterizedType)
                        .actualTypeArguments[i] as T
            } catch (e: ClassCastException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun <T> getClass(t: Any): Class<T> {
        // 通过反射 获取当前类的父类的泛型 (T) 对应 Class类
        return (t.javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments[0] as Class<T>
    }

    fun <T> getFiledClazz(field: Field): Class<T> {
        return field.genericType as Class<T>
    }
}