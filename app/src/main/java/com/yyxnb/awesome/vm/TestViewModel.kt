package com.yyxnb.awesome.vm

import android.arch.lifecycle.MutableLiveData
import com.yyxnb.http.BaseViewModel

class TestViewModel : BaseViewModel() {
    var result = MutableLiveData<String>()
}