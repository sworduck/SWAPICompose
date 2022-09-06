package com.example.swapicompose.data.cloud


class BaseCloudDataSource(private val service: ApiService) : CloudDataSource {
    override suspend fun getCharacterList(page: Int) = service.fetchCharacters(page)
    override suspend fun fetchFilmList() = service.fetchFilmList()
}