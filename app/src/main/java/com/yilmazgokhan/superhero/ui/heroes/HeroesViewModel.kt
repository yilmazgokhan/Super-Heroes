package com.yilmazgokhan.superhero.ui.heroes

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
 * View Model class for [HeroesFragment]
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class HeroesViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val mainRepository: MainRepository
) : BaseViewModel() {

    //region heroes
    private val _heroes = MutableLiveData<Resource<List<ResponseHero>>>()
    val heroes: LiveData<Resource<List<ResponseHero>>>
        get() = _heroes
    //endregion

    init {
        getAllHeroes()
    }

    /**
     * Send HTTP Request for get all heroes data
     */
    private fun getAllHeroes() {
        viewModelScope.launch {
            mainRepository.getAll().let {
                if (it.isSuccessful)
                    _heroes.postValue(Resource.success(it.body()))
                else
                    _heroes.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
}