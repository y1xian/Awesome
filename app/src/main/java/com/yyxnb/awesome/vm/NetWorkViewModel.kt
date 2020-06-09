package com.yyxnb.awesome.vm

import android.arch.lifecycle.MutableLiveData
import com.yyxnb.awesome.api.IService
import com.yyxnb.awesome.bean.StateData
import com.yyxnb.awesome.bean.TikTokBean
import com.yyxnb.awesome.data.Http
import com.yyxnb.common.AppConfig
import com.yyxnb.http.BaseViewModel
import com.yyxnb.http.interfaces.RequestDisplay

class NetWorkViewModel : BaseViewModel() {

    private val mApi: IService = Http.create(IService::class.java)

    var result = MutableLiveData<StateData<TikTokBean>>()

    fun videoList(value: String) {

//        val map = hashMapOf("key" to value)
        val map = hashMapOf<String,String>()


//                    page++;

        launchOnlyResult(
                //调用接口方法
                block = {
                    AppConfig.log(" vvvvvvv   2")
                    mApi.getVideoList(map)
                },
                //重试
                reTry = {
                    AppConfig.log(" vvvvvvv   rrrrr")
                    //调用重试的方法
                    videoList(value)
                },
                //成功
                success = {
                    AppConfig.log(" vvvvvvv   ssssssss")
                    //成功回调
                    result.value = it
//                    result.postValue(it)
                    //通知ui刷新
                }, type = RequestDisplay.NULL
        )
    }

}