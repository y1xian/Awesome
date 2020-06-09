package com.yyxnb.awesome.vm

import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import com.yyxnb.awesome.bean.TikTokBean
import com.yyxnb.http.BasePagedViewModel

class VideoViewModel : BasePagedViewModel<TikTokBean>() {
    override fun createDataSource(): DataSource<*, *> {
        return object : PageKeyedDataSource<Int?, TikTokBean?>() {
            override fun loadInitial(params: LoadInitialParams<Int?>, callback: LoadInitialCallback<Int?, TikTokBean?>) {
                callback.onResult(emptyList(), null, null)
            }

            override fun loadBefore(params: LoadParams<Int?>, callback: LoadCallback<Int?, TikTokBean?>) {
                callback.onResult(emptyList(), null)
            }

            override fun loadAfter(params: LoadParams<Int?>, callback: LoadCallback<Int?, TikTokBean?>) {
                callback.onResult(emptyList(), null)
            }
        }
    }
}