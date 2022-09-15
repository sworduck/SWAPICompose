package com.example.swapicompose.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.swapicompose.data.CharacterData
import com.example.swapicompose.data.cache.BaseCacheDataSource
import com.example.swapicompose.data.cache.character.CharacterDataBaseEntity
import com.example.swapicompose.domain.FavoriteUseCase
import com.example.swapicompose.utilis.Type
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val characterListFromCache: BaseCacheDataSource,
    private val clickFavoriteButton: FavoriteUseCase
) : ViewModel() {

    //private val characterListFromCache: BaseCacheDataSource = appModule.baseCacheDataSource
    //private val clickFavoriteButton: FavoriteUseCase = appModule.favoriteUseCase

    private val _listCharacter: MutableStateFlow<List<CharacterDataBaseEntity>> =
        MutableStateFlow(emptyList())
    val listCharacter: StateFlow<List<CharacterDataBaseEntity>> = _listCharacter

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getCharacterListByType() {
        CoroutineScope(Dispatchers.IO).launch {
            _isLoading.value = true
            _listCharacter.value = characterListFromCache.getCharacterListByType(Type.FAVORITE)
            _isLoading.value = false
        }
    }

    fun onClickFavoriteButton(characterData: CharacterData) {
        clickFavoriteButton.onClickFavoriteButton(characterData.type,
            characterData.id)
    }
}