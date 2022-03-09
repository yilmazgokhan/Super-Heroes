package com.yilmazgokhan.superhero.repository.api

import com.yilmazgokhan.superhero.data.remote.ResponseHero
import retrofit2.Response

/**
 * Methods of [ApiService]
 */
interface ApiHelper {

    suspend fun getHero(id: String): Response<ResponseHero>

    suspend fun getAll(): Response<List<ResponseHero>>
}