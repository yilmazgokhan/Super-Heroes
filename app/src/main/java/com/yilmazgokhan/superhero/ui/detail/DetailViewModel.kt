package com.yilmazgokhan.superhero.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yilmazgokhan.superhero.base.BaseViewModel
import com.yilmazgokhan.superhero.data.remote.ResponseHero
import com.yilmazgokhan.superhero.di.qualifier.IoDispatcher
import com.yilmazgokhan.superhero.repository.MainRepository
import com.yilmazgokhan.superhero.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View Model class for [DetailFragment]
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class DetailViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val mainRepository: MainRepository
) : BaseViewModel() {

    //region heroes
    private val _hero = MutableLiveData<Resource<ResponseHero>>()
    val hero: LiveData<Resource<ResponseHero>>
        get() = _hero
    //endregion

    /**
     * Send HTTP Request for get all heroes data
     */
    fun getHero(id: String) {
        viewModelScope.launch {
            mainRepository.getHero(id).let {
                if (it.isSuccessful)
                    _hero.postValue(Resource.success(it.body()))
                else
                    _hero.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
}