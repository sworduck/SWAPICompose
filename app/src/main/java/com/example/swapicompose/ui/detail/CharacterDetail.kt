package com.example.swapicompose.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.swapicompose.R
import com.example.swapicompose.data.CharacterData
import com.example.swapicompose.data.FilmData
import com.example.swapicompose.data.cache.character.CharacterDataBaseEntity
import com.example.swapicompose.ui.theme.Blue
import com.example.swapicompose.ui.theme.Navy
import com.example.swapicompose.utilis.CharacterDataUtil.TestCharacterData.testdata
import com.example.swapicompose.utilis.Type


@Preview(showBackground = true)
@Composable
fun DetailFragmentPreview() {
    CharacterDetailTest(testdata)
}

@Composable
fun CharacterDetail(
    navController: NavHostController, characterDataId: String,
    vm: CharacterDetailViewModel = viewModel(),
) {
    vm.viewCreated(characterDataId.toInt())

    val character: CharacterDataBaseEntity by vm.character.collectAsState()

    val filmList: List<FilmData> by vm.filmListLiveData.collectAsState()

    fun onClickFavorite(characterData: CharacterData) {
        vm.buttonClicked(characterData.id)
        when (characterData.type) {
            Type.FAVORITE -> {
                characterData.type = Type.DEFAULT
            }
            Type.DEFAULT -> {
                characterData.type = Type.FAVORITE
            }
        }
    }

    Column(Modifier.fillMaxHeight()) {
        var expanded by remember { mutableStateOf(false) }
        expanded = character.type == Type.FAVORITE

        Column(Modifier
            .fillMaxSize()
            .padding(5.dp)) {

            Text("Name: ${character.name} \nMass: ${character.mass}\nHeight: ${character.height}",
                Modifier
                    .background(
                        Navy, RectangleShape
                    )
                    .fillMaxWidth())

            LazyRow {
                items(items = filmList) { item ->
                    RowItem(item.title, item.opening_crawl)
                }
            }

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {

                Button(onClick = { navController.popBackStack() }) {
                    Text(text = "Back")
                }

                IconButton(
                    onClick = {
                        expanded = !expanded
                        onClickFavorite(character.mapToCharacterData())
                    }
                ) {

                    Icon(
                        painter = if (expanded) painterResource(id = R.drawable.ic_baseline_favorite_24)
                        else painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                        contentDescription = if (expanded) stringResource(R.string.favorite_button)
                        else stringResource(R.string.unfavorite_button)
                    )

                }
            }
        }
    }
}

@Composable
fun CharacterDetailTest(testcharacterData: CharacterData) {

    var expanded by remember { mutableStateOf(false) }

    Column(Modifier
        .fillMaxSize()
        .padding(5.dp)) {

        Text("${testcharacterData.name} моё имя\n ${testcharacterData.name} моё имя\n " +
                "${testcharacterData.name} моё имя",
            Modifier
                .background(
                    Navy, RectangleShape
                )
                .fillMaxWidth())

        val list = listOf(stringResource(R.string.testRow1), stringResource(R.string.testRow2),
            stringResource(R.string.testRow3))

        LazyRow {
            items(items = list) { item ->
                RowItem("название фильма", item)
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            Button(onClick = {}) {
                Text(text = "Back")
            }

            IconButton(
                onClick = { expanded = !expanded }
            ) {

                Icon(                                                       //заменить на OutLine.Sta
                    imageVector = if (expanded) Icons.Filled.Favorite else Icons.Filled.Clear,
                    contentDescription = if (expanded) stringResource(R.string.favorite_button)
                    else stringResource(R.string.unfavorite_button)
                )

            }
        }
    }
}

@Composable
fun RowItem(title: String, openingCrawl: String) {

    Column(Modifier
        .padding(horizontal = 5.dp, vertical = 10.dp)
        .background(Navy, RectangleShape)
        .width(200.dp)) {

        Text(text = title,
            Modifier
                .background(Blue, RectangleShape)
                .fillMaxWidth())

        Text(text = openingCrawl,
            Modifier
                .width(200.dp)
                .heightIn(300.dp, 500.dp))

    }

}