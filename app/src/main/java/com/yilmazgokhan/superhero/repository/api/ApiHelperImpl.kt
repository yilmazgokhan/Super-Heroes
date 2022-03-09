package com.yilmazgokhan.superhero.repository.api

import com.yilmazgokhan.superhero.data.remote.ResponseHero
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation class
 */
class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getHero(id: String): Response<ResponseHero> =
        apiService.getHero(id)

    override suspend fun getAll(): Response<List<ResponseHero>> =
        apiService.getAll()
}