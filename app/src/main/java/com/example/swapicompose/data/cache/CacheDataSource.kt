package com.example.swapicompose.data.cache

import com.example.swapicompose.data.CharacterData
import com.example.swapicompose.data.cache.character.CharacterDataBaseEntity
import com.example.swapicompose.data.cache.film.FilmDataBaseEntity
import com.example.swapicompose.utilis.Type

interface CacheDataSource {
    fun fetchDataFromDB(): List<CharacterDataBaseEntity>

    fun getCharacter(id: Int): CharacterDataBaseEntity

    fun getCharacterListByType(type: Type): List<CharacterDataBaseEntity>

    fun updateCharacter(characterDataBaseEntity: CharacterDataBaseEntity)

    suspend fun saveData(characterDataList: List<CharacterData>)

    fun fetchFilmList(): List<FilmDataBaseEntity>

    suspend fun saveFilmList(filmDataBaseEntityList: List<FilmDataBaseEntity>)
}