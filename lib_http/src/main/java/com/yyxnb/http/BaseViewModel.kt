package com.yyxnb.http

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import android.view.View
import com.madreain.libhulk.http.exception.NetWorkException
import com.madreain.libhulk.http.exception.ResultException
import com.madreain.libhulk.http.exception.ReturnCodeException
import com.yyxnb.common.AppConfig
import com.yyxnb.common.NetworkUtils
import com.yyxnb.http.exception.ResponseThrowable
import com.yyxnb.common.interfaces.IData
import com.yyxnb.http.interfaces.RequestDisplay
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    //网络请求展示类型
    private var type: RequestDisplay? = null

    /**
     * 网络相关工具
     */
    val networkUtils: NetworkUtils by lazy { NetworkUtils }

    //重试的监听
    var listener: View.OnClickListener? = null

    val mScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = mScope.launch { block() }

    /**
     * 用流的方式进行网络请求
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }

    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param type RequestDisplay类型 NULL无交互  TOAST  REPLACE 替换
     * @param view
     *
     **/
    fun <T> launchOnlyResult(
            block: suspend CoroutineScope.() -> IData<T>,
            //成功
            success: (T) -> Unit = {},
            //错误 根据错误进行不同分类
            error: (Throwable) -> Unit = {
                if (!networkUtils.isConnected) {
                    onNetWorkError { reTry() }//没网
                } else {
                    if (it is NetWorkException) {
                        onNetWorkError { reTry() }
                    } else if (it is ReturnCodeException) {
                        onReturnCodeError(
                                it.returnCode,
                                it.message
                        ) { reTry() }
                    } else if (it is ResultException) {
                        onTEmpty { reTry() }
                    } else {
                        onNetWorkError { reTry() } //UnknownHostException 1：服务器地址错误；2：网络未连接
                    }
                }
            },
            //完成
            complete: () -> Unit = {},
            //重试
            reTry: () -> Unit = {},
            //接口操作交互类型
            type: RequestDisplay = RequestDisplay.NULL
    ) {
        //接口操作交互类型赋值
        this.type = type
        //开始请求接口前
        when (type) {
            RequestDisplay.NULL -> {
            }
            RequestDisplay.TOAST -> {
//                viewChange.showDialogProgress.value = ""
            }
            RequestDisplay.REPLACE -> {
//                viewChange.showLoading.call()
            }
        }
        //正式请求接口
        launchUI {
            //异常处理
            handleException(
                    //调用接口
                    { withContext(Dispatchers.IO) { block() } },
                    { res ->
                        //接口成功返回
                        executeResponse(res) {
                            success(it)
                        }
                    },
                    {
                        //接口失败返回
                        error(it)
                    },
                    {
                        //接口完成
                        complete()
                    }
            )
        }
    }


    /**
     * 请求结果过滤
     */
    private suspend fun <T> executeResponse(
            response: IData<T>,
            success: suspend CoroutineScope.(T) -> Unit
    ) {
        coroutineScope {
            //接口成功返回后判断是否是增删改查成功，不满足的话，返回异常
                if (response.isSuccess()) {
                    if (response.getResult() == null || response.getResult().toString() == "[]") {
                        AppConfig.log("eeeeee")
                        //完成的回调所有弹窗消失
//                        viewChange.dismissDialog.call()
//                        viewChange.showEmpty.call()
                    } else {
                        AppConfig.log(" sssss")
                        success(response.getResult())
                        //完成的回调所有弹窗消失
//                        viewChange.dismissDialog.call()
//                        viewChange.restore.call()
                    }
                } else {
                    //状态码错误
                    throw ResponseThrowable(
                            response.getCode()!!,
                            response.getMsg()!!
                    )
                }
        }
    }

    /**
     * 异常统一处理
     */
    private suspend fun <T> handleException(
            block: suspend CoroutineScope.() -> IData<T>,
            success: suspend CoroutineScope.(IData<T>) -> Unit,
            error: suspend CoroutineScope.(Throwable) -> Unit,
            complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                error(e)
                e.printStackTrace()
            } finally {
                complete()
            }
        }
    }


    /**
     * 数据为空
     */
    private fun onTEmpty( //重试
            reTry: () -> Unit = {
            }
    ) {
        when (type) {
            RequestDisplay.NULL -> {
            }
            RequestDisplay.TOAST -> {
//                viewChange.showToast.value = emptyMsg
//                viewChange.dismissDialog.call()
            }
            RequestDisplay.REPLACE -> {
                this.listener = View.OnClickListener {
                    reTry()
                }
//                viewChange.showEmpty.value = emptyMsg

            }
        }
    }

    /**
     * 网络异常
     */
    private fun onNetWorkError(
            reTry: () -> Unit = {
            }
    ) {
        when (type) {
            RequestDisplay.NULL -> {

            }
            RequestDisplay.TOAST -> {
//                viewChange.showToast.value = errorMsg
//                viewChange.dismissDialog.call()
            }
            RequestDisplay.REPLACE -> {
                this.listener = View.OnClickListener {
                    reTry()
                }
//                viewChange.showNetworkError.value = errorMsg
            }
        }
    }

    /**
     * 返回code错误
     */
    private fun onReturnCodeError(
            returnCode: String,
            message: String?,
            reTry: () -> Unit = {
            }
    ) {
        when (type) {
            RequestDisplay.NULL -> {
            }
            RequestDisplay.TOAST -> {
//                viewChange.showToast.value = message
//                viewChange.dismissDialog.call()
            }
            RequestDisplay.REPLACE -> {
                this.listener = View.OnClickListener {
                    reTry()
                }
//                viewChange.showNetworkError.value = message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mScope.cancel()
        listener = null
    }
}