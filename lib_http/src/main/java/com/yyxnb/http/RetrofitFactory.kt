package com.yyxnb.http

import com.yyxnb.http.annotations.BaseUrl
import com.yyxnb.http.annotations.BindUrl
import okhttp3.HttpUrl
import retrofit2.Retrofit
import java.util.*

/**
 * retrofit 工厂类
 */
object RetrofitFactory {

    var urlMap: MutableMap<String, HttpUrl> = HashMap()

    fun <T> create(clz: Class<T>): T {
        return create(clz, null)
    }

    fun <T> create(clz: Class<T>, retrofitConfig: BaseRetrofitConfig?): T {
        val baseUrl = prepareBaseUrl(clz)
        prepareOtherUrls(clz)

        // 判断是否, 单独设置了 retrofitConfig, 否则默认按照全局 RetrofitConfig 配置
        var retrofit: Retrofit? = null
        retrofit = if (retrofitConfig != null) {
            retrofitConfig.initRetrofit(baseUrl)
        } else {
            OkHttpConfig.getInstance().retrofitConfig.initRetrofit(baseUrl)
        }
        return retrofit.create(clz)
    }

    private fun <T> prepareOtherUrls(clz: Class<T>) {
        val annotation: Annotation? = clz.getAnnotation(BindUrl::class.java)
        if (annotation != null) {
            for (url in urlMap.keys) {
                urlMap[url] = Objects.requireNonNull(HttpUrl.parse(url))!!
            }
        }
    }

    private fun <T> prepareBaseUrl(clz: Class<T>): String {
        val baseUrlAnnotation = clz.getAnnotation(BaseUrl::class.java)
        return baseUrlAnnotation?.value ?: throw IllegalArgumentException("base url is null")
    }

}