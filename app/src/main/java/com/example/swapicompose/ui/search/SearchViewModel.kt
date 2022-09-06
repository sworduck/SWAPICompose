package com.example.swapicompose.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swapicompose.MainActivity.Companion.appModule
import com.example.swapicompose.data.BaseSearchRepository
import com.example.swapicompose.data.CharacterData
import com.example.swapicompose.data.cache.BaseCacheDataSource
import com.example.swapicompose.data.cloud.BaseCloudDataSource
import com.example.swapicompose.data.cloud.film.FilmCloudList
import com.example.swapicompose.domain.FavoriteUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException


class SearchViewModel():ViewModel() {

    private val characterListFromCloud: BaseCloudDataSource = appModule.baseCloudDataSource
    private val characterListFromCache: BaseCacheDataSource = appModule.baseCacheDataSource
    private val clickFavoriteButton: FavoriteUseCase = appModule.favoriteUseCase

    private val _characterDataList: MutableLiveData<List<CharacterData>> = MutableLiveData()
    val characterDataList: LiveData<List<CharacterData>> = _characterDataList

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> = _errorMessage

    fun viewCreated() {
        getCharacterList()
        CoroutineScope(Dispatchers.IO).launch {
            val filmDbList = characterListFromCache.fetchFilmList()
            if (filmDbList.isEmpty()) {
                val typeCharacter = object : TypeToken<FilmCloudList>() {}.type
                val filmList: FilmCloudList =
                    Gson().fromJson(characterListFromCloud.fetchFilmList().string(), typeCharacter)
                characterListFromCache.saveFilmList(filmList.results?.let { filmCloudList ->
                    filmCloudList.mapIndexed { id, filmCloud ->
                        filmCloud.mapToFilmDataBaseEntity(id)
                    }
                }.orEmpty())
            }
        }
    }

    private fun getCharacterList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _characterDataList.postValue(BaseSearchRepository(characterListFromCloud,
                    characterListFromCache).fetchCharacterList())
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> _errorMessage.postValue("Отсутсвует интернет, попробуйте еще раз")
                    is UnknownHostException -> _errorMessage.postValue("Отсутсвует интернет, попробуйте еще раз")
                    else -> _errorMessage.postValue("Отсутсвует интернет, попробуйте еще раз")
                }
            }
        }
    }

    fun retryClicked(visible: Boolean) {
        if (visible) {
            getCharacterList()
        }
    }

    fun onClickFavoriteButton(characterData: CharacterData) {
        clickFavoriteButton.onClickFavoriteButton(characterData.type,
            characterData.id)
    }
}