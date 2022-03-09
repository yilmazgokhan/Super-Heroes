package com.yilmazgokhan.superhero.repository

import com.yilmazgokhan.superhero.repository.api.ApiHelper
import javax.inject.Inject

/**
 * Class for HTTP request via Retrofit
 */
class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getHero(id: String) = apiHelper.getHero(id)

    suspend fun getAll() = apiHelper.getAll()
}