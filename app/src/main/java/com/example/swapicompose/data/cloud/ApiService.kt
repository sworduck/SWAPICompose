package com.example.swapicompose.data.cloud

import com.example.swapicompose.data.cloud.character.CharacterCloudList
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://swapi.dev/"
    }

    @GET("api/people")
    suspend fun fetchCharacters(@Query("page") id: Int): CharacterCloudList

    @GET("api/people/{id}")
    suspend fun fetchCharacter(@Path("id") id: Int): ResponseBody

    @GET("api/films")
    suspend fun fetchFilmList(): ResponseBody
}