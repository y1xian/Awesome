package com.yyxnb.awesome.data

import com.yyxnb.http.AbstractHttp

object Http : AbstractHttp() {

    override fun baseUrl(): String {
       return DataConfig.BASE_URL
    }


}