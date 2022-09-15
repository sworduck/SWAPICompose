package com.example.swapicompose.ui.detail

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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
import com.example.swapicompose.ui.theme.SWAPIComposeTheme
import com.example.swapicompose.utilis.CharacterDataUtil.TestCharacterData.testdata
import com.example.swapicompose.utilis.Type

@Preview(showBackground = true,uiMode = UI_MODE_NIGHT_NO)
@Composable
fun DetailFragmentPreviewLight() {
    SWAPIComposeTheme {
        CharacterDetailTest(testdata)
    }
}

@Preview(showBackground = true,uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailFragmentPreviewNight() {
    SWAPIComposeTheme {
        CharacterDetailTest(testdata)
    }
}

@Composable
fun CharacterDetail(
    navController: NavHostController, characterDataId: String,
    vm: CharacterDetailViewModel,
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
                        MaterialTheme.colors.primary, RectangleShape
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

        Text("Name: ${testcharacterData.name}\nMass: ${testcharacterData.mass}\n " +
                "Height: ${testcharacterData.height}",
            Modifier.background(MaterialTheme.colors.primary)
                .fillMaxWidth())

        val listName = listOf("A New Hope", "The Empire Strikes Back", "Return of the Jedi")
        val listDescription = listOf(stringResource(R.string.testRow1), stringResource(R.string.testRow2),
            stringResource(R.string.testRow3))

        LazyRow {
            item {
                RowItem(listName[0], listDescription[0])
            }
            item {
                RowItem(listName[1], listDescription[1])
            }
            item {
                RowItem(listName[2], listDescription[2])
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
        .background(MaterialTheme.colors.primary, RectangleShape)
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