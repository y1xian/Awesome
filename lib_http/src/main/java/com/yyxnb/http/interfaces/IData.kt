package com.yyxnb.http.interfaces

/**
 * 接口返回封装类
 * @param <T>
</T> */
interface IData<T> {

    fun getMsg(): String?

    fun getCode(): String?

    fun getResult(): T

}