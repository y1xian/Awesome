package com.yyxnb.http.config

import com.madreain.libhulk.http.interceptor.IReturnCodeErrorInterceptor
import com.madreain.libhulk.http.interceptor.IVersionDifInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.*

/**
 * @author madreain
 * @date 2020/3/13.
 * module：
 * description：
 */
object HttpConfig {

    var retrofit: Retrofit? = null
    var okHttpClient: OkHttpClient? = null

    var okHttpClientBuilder = OkHttpClient.Builder()

    //服务地址
    var baseUrl: String? = null

    //returnCode 正常态的值 真对不同接口返回支持单正常态值的返回，也支持增删改查不同正常态值的返回
    var retSuccess: String? = null

    var retSuccessList: List<String>? = null

    //网络相关
    //日志开关
    var logOpen = false

    //连接超时时间 单位秒
    var connectTimeout: Long = 10L

    //读超时时间
    var readTimeout: Long = 10L

    //写超时时间
    var writeTimeout: Long = 10L

    //异常处理的 相关拦截器
    //oKHttp拦截器
    var okHttpInterceptors: MutableList<Interceptor>? = null

    //接口返回ReturnCode不是正常态拦截器
    var retCodeInterceptors: MutableList<IReturnCodeErrorInterceptor>? = null

    //服务端版本和本地版本不一致拦截器
    var versionDifInterceptors: MutableList<IVersionDifInterceptor>? = null

    //是否开启缓存
    var cacheOpen = false

}