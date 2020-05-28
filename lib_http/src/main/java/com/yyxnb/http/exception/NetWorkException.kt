package com.madreain.libhulk.http.exception

/**
 * 自定义网络错误异常
 */
class NetWorkException : Exception {
    /**
     * 网络错误
     */
    constructor() : super() {}

    /**
     * 网络错误传入错误内容
     * @param msg
     */
    constructor(msg: String?) : super(msg) {}
}