package com.yyxnb.arch.livedata

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.yyxnb.common.AppConfig.getFiledClazz
import java.io.Serializable
import java.lang.reflect.Field

object ViewModelFactory : Serializable {
    /**
     * 创建 对应的 ViewModel, 并且 添加 通用 (LiveData) 到 ViewModel中
     */
    fun createViewModel(fragment: Fragment, field: Field): ViewModel {
        val viewModelClass = getFiledClazz<ViewModel>(field)
        return ViewModelProviders.of(fragment)[viewModelClass]
    }

    /**
     * 创建 对应的 ViewModel, 并且 添加 通用 (LiveData) 到 ViewModel中
     */
    fun createViewModel(activity: FragmentActivity, field: Field): ViewModel {
        val viewModelClass = getFiledClazz<ViewModel>(field)
        return ViewModelProviders.of(activity)[viewModelClass]
    }
}