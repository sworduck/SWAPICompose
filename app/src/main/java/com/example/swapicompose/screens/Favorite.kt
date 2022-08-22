package com.example.swapicompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.swapicompose.BottomNavigation
import com.example.swapicompose.CharacterData
import com.example.swapicompose.Type

@Preview(showBackground = true)
@Composable
fun FavoriteFragmentPreview(){
    FavoriteScreen()
}

@Composable
fun FavoriteScreen(){

    Column(Modifier.fillMaxHeight()) {
        LazyColumnList(
            listOf(
                CharacterData(1, "name1", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(2, "name2", "1", "1", "1", "1", Type.DEFAULT)
            ), Modifier.padding(
                bottom = 50.dp
            )
        )
    }
}

