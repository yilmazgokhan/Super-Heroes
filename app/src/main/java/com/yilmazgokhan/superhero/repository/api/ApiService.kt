package com.yilmazgokhan.superhero.repository.api

import com.yilmazgokhan.superhero.data.remote.ResponseHero
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Class for API endpoints for Retrofit
 */
interface ApiService {

    @GET("id/{id}.json")
    suspend fun getHero(@Path("id") id: String): Response<ResponseHero>

    @GET("all.json")
    suspend fun getAll(): Response<List<ResponseHero>>
}