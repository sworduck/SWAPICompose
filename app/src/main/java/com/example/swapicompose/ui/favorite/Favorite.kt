package com.example.swapicompose.ui.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.swapicompose.data.CharacterData
import com.example.swapicompose.ui.search.CharacterListItem
import com.example.swapicompose.ui.search.LoadingUi
import com.example.swapicompose.utilis.Type

@Composable
fun FavoriteScreen(
    navController: NavHostController,
    vm: FavoriteViewModel = viewModel(),
) {

    val users by vm.listCharacter.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    vm.getCharacterListByType()

    fun onClickFavorite(characterData: CharacterData) {
        vm.onClickFavoriteButton(characterData)
        when (characterData.type) {
            Type.FAVORITE -> {
                characterData.type = Type.DEFAULT
            }
            Type.DEFAULT -> {
                characterData.type = Type.FAVORITE
            }
        }
    }

    when {
        isLoading -> {
            LoadingUi()
        }
        else -> {
            Column(Modifier
                .fillMaxHeight()
                .padding(top = 20.dp)) {
                LazyColumnList(
                    users.map { it.mapToCharacterData() },
                    Modifier.fillMaxHeight(),
                    navController,
                    ::onClickFavorite)
            }
        }
    }
}

@Composable
fun LazyColumnList(
    messages: List<CharacterData>,
    modifier: Modifier,
    navController: NavHostController,
    onClickFavorite: (CharacterData) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(items = messages) { message ->
            CharacterListItem(message, navController, onClickFavorite)
        }
    }
}

