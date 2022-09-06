package com.example.swapicompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.swapicompose.data.CharacterData
import com.example.swapicompose.ui.favorite.FavoriteViewModel
import com.example.swapicompose.utilis.Type

/*
@Preview(showBackground = true)
@Composable
fun FavoriteFragmentPreview(){
    FavoriteScreen()
}

 */

@Composable
fun FavoriteScreen(
    navController: NavHostController,
    vm:FavoriteViewModel = viewModel(),
){

    val users = vm.listCharacter.observeAsState()
    vm.getCharacterListByType()

    fun onClickFavorite(characterData: CharacterData) {
        vm.onClickFavoriteButton(characterData)
    }

    if(users.value?.isNotEmpty() == true) {
        Column(Modifier.fillMaxHeight()) {
            LazyColumnList(
                vm.listCharacter.value.let { it!!.map {characterData-> characterData.mapToCharacterData() } }, Modifier.fillMaxHeight()
                ,navController, ::onClickFavorite)
        }
    }
}

