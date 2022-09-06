package com.example.swapicompose.data.cache

import com.example.swapicompose.data.CharacterData
import com.example.swapicompose.data.cache.character.CharacterDataBaseDao
import com.example.swapicompose.data.cache.character.CharacterDataBaseEntity
import com.example.swapicompose.data.cache.film.FilmDataBaseDao
import com.example.swapicompose.data.cache.film.FilmDataBaseEntity
import com.example.swapicompose.utilis.Type

class BaseCacheDataSource(db: SwapiRoomDataBase) : CacheDataSource {

    private val characterDataBaseDao: CharacterDataBaseDao = db.characterDataBaseDao()
    private val filmDataBaseDao: FilmDataBaseDao = db.filmDataBaseDao()

    override fun fetchDataFromDB(): List<CharacterDataBaseEntity> {
        return characterDataBaseDao.getCharacterList()
    }

    override fun updateCharacter(characterDataBaseEntity: CharacterDataBaseEntity) {
        characterDataBaseDao.update(characterDataBaseEntity)
    }

    override fun getCharacter(id: Int): CharacterDataBaseEntity {
        return characterDataBaseDao.getCharacter(id)
    }

    override fun getCharacterListByType(type: Type): List<CharacterDataBaseEntity> {
        return characterDataBaseDao.getCharacterListByType(type)
    }

    override suspend fun saveData(characterDataList: List<CharacterData>) {
        characterDataBaseDao.insertList(characterDataList.map { characterData -> characterData.mapToCharacterDataBaseEntity() })
    }

    override fun fetchFilmList(): List<FilmDataBaseEntity> {
        return filmDataBaseDao.getAllFilm()
    }

    override suspend fun saveFilmList(filmDataBaseEntityList: List<FilmDataBaseEntity>) {
        filmDataBaseDao.insertList(filmDataBaseEntityList)
    }
}