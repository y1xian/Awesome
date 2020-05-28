package com.yyxnb.arch.annotations

@Target(AnnotationTarget.FIELD)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class BindViewModel(val isActivity: Boolean = false)