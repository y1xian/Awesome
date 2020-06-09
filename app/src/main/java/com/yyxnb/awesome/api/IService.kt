package com.yyxnb.awesome.api

import com.yyxnb.awesome.bean.BaseData
import com.yyxnb.awesome.bean.StateData
import com.yyxnb.awesome.bean.TikTokBean
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface IService {

    //    @FormUrlEncoded
    //    @POST("video/query")
    //    LiveData<ApiResponse<BaseListData<TikTokBean>>> getVideoList(@FieldMap Map<String,String> map);

    @GET("v2/5ecfd21e320000f1aee3d61a")
//    @GET("video/query")
    suspend fun getVideoList(@QueryMap map: Map<String, String>): BaseData<StateData<TikTokBean>>

    @GET("v2/5ecfd21e320000f1aee3d61a")
    //    @GET("video/query")
    suspend fun getVideoList4(@QueryMap map: Map<String?, String?>?): BaseData<StateData<TikTokBean>>
}