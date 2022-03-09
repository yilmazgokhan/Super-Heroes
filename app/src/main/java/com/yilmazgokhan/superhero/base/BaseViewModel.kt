package com.yilmazgokhan.superhero.base

import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.LogUtils

/**
 * Base class for ViewModel
 */
abstract class BaseViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        LogUtils.d("$this onCleared")
    }
}