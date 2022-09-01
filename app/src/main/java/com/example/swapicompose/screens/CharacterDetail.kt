package com.example.swapicompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swapicompose.CharacterData
import com.example.swapicompose.R
import com.example.swapicompose.util.CharacterDataUtil.TestCharacterData.testdata


@Preview(showBackground = true)
@Composable
fun DetailFragmentPreview(){
    CharacterDetailTest(testdata)
}



@Composable
fun CharacterDetail(navController: NavHostController,characterData: String){
    Column(Modifier.fillMaxHeight()) {
        /*
        Text("${characterData} моё имя")

        Button(onClick = {navController.popBackStack()}) {
            Text(text = "Back")
        }

         */
        val id = characterData
        var expanded by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxSize()) {
            Text("${id}1 моё имя")
            Text("${id}2 моё имя")
            Text("${id}3 моё имя")
            val list = listOf(stringResource(R.string.testRow1), stringResource(R.string.testRow2),
                stringResource(R.string.testRow3))
            LazyRow(){
                items(items = list){ item->
                    RowItem(item)
                }
            }
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                Button(onClick = {navController.popBackStack()}) {
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
}

@Composable
fun CharacterDetailTest(testcharacterData: CharacterData) {
    var expanded by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize()) {
        Text("${testcharacterData.name} моё имя")
        Text("${testcharacterData.name} моё имя")
        Text("${testcharacterData.name} моё имя")
        val list = listOf(stringResource(R.string.testRow1), stringResource(R.string.testRow2),
            stringResource(R.string.testRow3))
        LazyRow(){
            items(items = list){ item->
                RowItem(item)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
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
fun RowItem(item: String) {
    Text(text = item,Modifier.width(200.dp))
}