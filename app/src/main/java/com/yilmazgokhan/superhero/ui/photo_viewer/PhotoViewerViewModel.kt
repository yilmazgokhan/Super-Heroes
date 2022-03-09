package com.yilmazgokhan.superhero.ui.photo_viewer

import com.blankj.utilcode.util.LogUtils
import com.yilmazgokhan.superhero.base.BaseViewModel
import com.yilmazgokhan.superhero.di.qualifier.IoDispatcher
import com.yilmazgokhan.superhero.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * View Model class for [PhotoViewerFragment]
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class PhotoViewerViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val mainRepository: MainRepository
) : BaseViewModel() {

    init {
        LogUtils.d("$this initialized")
    }
}