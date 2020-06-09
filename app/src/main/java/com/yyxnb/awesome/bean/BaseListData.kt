package com.yyxnb.awesome.bean

import java.io.Serializable

class BaseListData<T> : BaseData<T>(), Serializable {
    var list: List<T>? = null
}