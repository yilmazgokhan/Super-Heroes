package com.yilmazgokhan.superhero.ui.main

import com.blankj.utilcode.util.LogUtils
import com.yilmazgokhan.superhero.base.BaseViewModel
import com.yilmazgokhan.superhero.di.qualifier.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * ViewModel class for [MainActivity]
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    init {
        LogUtils.d("$this initialized")
    }
}