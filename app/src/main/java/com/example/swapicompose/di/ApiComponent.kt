package com.example.swapicompose.di

import com.example.swapicompose.data.cloud.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiComponent {
    val apiService:ApiService
}

object ApiModule : ApiComponent {

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }

}