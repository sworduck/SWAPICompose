package com.example.swapicompose.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swapicompose.MainActivity.Companion.appModule
import com.example.swapicompose.data.BaseSearchRepository
import com.example.swapicompose.data.CharacterData
import com.example.swapicompose.data.cache.BaseCacheDataSource
import com.example.swapicompose.data.cloud.BaseCloudDataSource
import com.example.swapicompose.data.cloud.film.FilmCloudList
import com.example.swapicompose.domain.FavoriteUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val characterListFromCloud: BaseCloudDataSource,
    private val characterListFromCache: BaseCacheDataSource,
    private val clickFavoriteButton: FavoriteUseCase
) : ViewModel() {

    private var check = true

    //private val characterListFromCloud: BaseCloudDataSource = appModule.baseCloudDataSource
    //private val characterListFromCache: BaseCacheDataSource = appModule.baseCacheDataSource
    //private val clickFavoriteButton: FavoriteUseCase = appModule.favoriteUseCase

    private val _characterDataList: MutableStateFlow<List<CharacterData>> =
        MutableStateFlow(emptyList())
    val characterDataList: StateFlow<List<CharacterData>> = _characterDataList

    private val _errorMessage: MutableStateFlow<String> = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun viewCreated() {
        getCharacterList()
        viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                if (check) {
                    check = false
                    throw HttpException(Response.error<ResponseBody>(500,
                        ResponseBody.create(MediaType.parse("plain/text"), "some content")))
                }
                _characterDataList.value = BaseSearchRepository(characterListFromCloud,
                    characterListFromCache).fetchCharacterList()
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> _errorMessage.value =
                        "Отсутсвует интернет, попробуйте еще раз"
                    is UnknownHostException -> _errorMessage.value =
                        "Отсутсвует интернет, попробуйте еще раз"
                    else -> _errorMessage.value = "Отсутсвует интернет, попробуйте еще раз"
                }
            }
            _isLoading.value = false
        }
    }

    fun retryClicked() {
        getCharacterList()
        _errorMessage.value = ""
    }

    fun onClickFavoriteButton(characterData: CharacterData) {
        clickFavoriteButton.onClickFavoriteButton(characterData.type,
            characterData.id)
    }
}