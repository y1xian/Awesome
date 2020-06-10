package com.yyxnb.http.interceptor

import com.yyxnb.common.AppConfig
import com.yyxnb.http.utils.JsonUtils.formatJson
import okhttp3.logging.HttpLoggingInterceptor
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

class LoggingInterceptor : HttpLoggingInterceptor.Logger {
    private val mMessage = StringBuilder()
    override fun log(text: String) {
        try {
            var message = URLDecoder.decode(text, "utf-8").replace("\\", "")
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0)
                mMessage.append(" ")
                mMessage.append("\r\n")
            }
            if (message.startsWith("--> GET")) {
                mMessage.setLength(0)
                mMessage.append(" ")
                mMessage.append("\r\n")
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if (message.startsWith("{") && message.endsWith("}") || message.startsWith("[") && message.endsWith("]")) {
                message = formatJson(message)
            }
            mMessage.append(message).append("\r\n")
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                AppConfig.getInstance().log("Http", mMessage.toString())
            }
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }
}