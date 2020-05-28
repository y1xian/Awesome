package com.yyxnb.awesome.api

import com.yyxnb.awesome.bean.BaseData
import com.yyxnb.awesome.bean.StateData
import com.yyxnb.awesome.bean.TikTokBean
import com.yyxnb.awesome.data.DataConfig
import com.yyxnb.http.annotations.BaseUrl
import retrofit2.http.GET
import retrofit2.http.QueryMap

@BaseUrl(DataConfig.BASE_URL)
interface IService {

    //    @FormUrlEncoded
    //    @POST("video/query")
    //    LiveData<ApiResponse<BaseListData<TikTokBean>>> getVideoList(@FieldMap Map<String,String> map);

    @GET("video/query")
    suspend fun getVideoList(@QueryMap map: Map<String, String>): BaseData<StateData<TikTokBean>>


}