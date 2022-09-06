package com.example.swapicompose.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swapicompose.MainActivity.Companion.appModule
import com.example.swapicompose.data.CharacterData
import com.example.swapicompose.data.cache.BaseCacheDataSource
import com.example.swapicompose.data.cache.character.CharacterDataBaseEntity
import com.example.swapicompose.domain.FavoriteUseCase
import com.example.swapicompose.utilis.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel:ViewModel() {

    private val characterListFromCache: BaseCacheDataSource = appModule.baseCacheDataSource
    private val clickFavoriteButton: FavoriteUseCase = appModule.favoriteUseCase

    private val _listCharacter: MutableLiveData<List<CharacterDataBaseEntity>> = MutableLiveData()
    val listCharacter: LiveData<List<CharacterDataBaseEntity>> = _listCharacter

    fun getCharacterListByType() {
        CoroutineScope(Dispatchers.IO).launch {
            _listCharacter.postValue(characterListFromCache.getCharacterListByType(Type.FAVORITE))
        }
    }

    fun onClickFavoriteButton(characterData: CharacterData) {
        clickFavoriteButton.onClickFavoriteButton(characterData.type,
            characterData.id)
    }
}