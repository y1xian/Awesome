package com.yyxnb.awesome.bean

import com.yyxnb.common.interfaces.IData
import java.io.Serializable
import java.util.*

data class MainBean
(
        var id: Int = 0,
        var type: Int = 0,
        var title: String? = null,
        var des: String? = null,
        var url: String? = null
) : IData<Long?>, Serializable {

    override fun id(): Int {
        return id
    }

    override fun getCode(): String? {
        return null
    }

    override fun getMsg(): String? {
        return des
    }

    override fun getResult(): Long {
        return 0L
    }

    override fun isSuccess(): Boolean {
        return false
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is MainBean) return false
        val mainBean = o
        return id == mainBean.id && type == mainBean.type &&
                title == mainBean.title &&
                des == mainBean.des &&
                url == mainBean.url
    }

    override fun hashCode(): Int {
        return Objects.hash(id, type, title, des, url)
    }
}