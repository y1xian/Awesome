package com.yyxnb.awesome.bean

import com.yyxnb.common.interfaces.IData
import java.io.Serializable

open class BaseData<T> : IData<T>, Serializable {
    var status: String? = null
    var message: String? = null
    var data: T? = null
    override fun getCode(): String? {
        return status
    }

    override fun getMsg(): String? {
        return message
    }

    override fun getResult(): T {
        return data!!
    }

    override fun isSuccess(): Boolean {
        return "200" == status
    }

    override fun id(): Int {
        return hashCode()
    }
}