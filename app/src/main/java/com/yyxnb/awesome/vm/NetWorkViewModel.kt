package com.yyxnb.awesome.vm

import android.arch.lifecycle.MutableLiveData
import com.yyxnb.awesome.api.IService
import com.yyxnb.awesome.bean.StateData
import com.yyxnb.awesome.bean.TikTokBean
import com.yyxnb.awesome.data.Http
import com.yyxnb.http.BaseViewModel

class NetWorkViewModel : BaseViewModel() {

    private val mApi: IService = Http.create(IService::class.java)

    val result = MutableLiveData<StateData<TikTokBean>>()

    fun videoList(value: String) {

        val map = hashMapOf<String, String>()

        launchOnlyResult(
                //调用接口方法
                block = {
                    mApi.getVideoList(map)
                },
                //重试
                reTry = {
                    //调用重试的方法
                    videoList(value)
                },
                //成功
                success = {
                    //成功回调
                    result.value = it
                }
        )
    }

}