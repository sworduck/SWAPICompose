package com.example.swapicompose.ui.detail

import androidx.lifecycle.ViewModel
import com.example.swapicompose.MainActivity.Companion.appModule
import com.example.swapicompose.data.FilmData
import com.example.swapicompose.data.cache.BaseCacheDataSource
import com.example.swapicompose.data.cache.character.CharacterDataBaseEntity
import com.example.swapicompose.data.cache.film.FilmDataBaseEntity
import com.example.swapicompose.utilis.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {

    private val characterListFromCache: BaseCacheDataSource = appModule.baseCacheDataSource

    private val _filmListLiveData: MutableStateFlow<List<FilmData>> = MutableStateFlow(emptyList())
    val filmListLiveData: StateFlow<List<FilmData>> = _filmListLiveData

    private val _character: MutableStateFlow<CharacterDataBaseEntity> =
        MutableStateFlow(CharacterDataBaseEntity(-1, "1", "1", "1", "1", Type.DEFAULT, "1"))
    val character: StateFlow<CharacterDataBaseEntity> = _character

    fun viewCreated(position: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            _character.value = characterListFromCache.getCharacter(position)
            val list: List<Int> = _character.value.idList.split(",").map {
                it.replace("/", "").substringAfterLast("films").toInt()
            }
            val filmListDb = characterListFromCache.fetchFilmList()
            val filmsDb: MutableList<FilmDataBaseEntity> = mutableListOf()

            list.forEach {
                filmsDb.add(filmListDb[it - 1])
            }

            _filmListLiveData.value =
                filmsDb.map { filmDataBaseEntity -> filmDataBaseEntity.mapToFilmData() }
        }
    }

    fun buttonClicked(position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val character = characterListFromCache.getCharacter(position)
            when (character.type) {
                Type.DEFAULT -> {
                    character.type = Type.FAVORITE
                }
                Type.FAVORITE -> {
                    character.type = Type.DEFAULT
                }
            }

            characterListFromCache.updateCharacter(character)
        }
    }
}