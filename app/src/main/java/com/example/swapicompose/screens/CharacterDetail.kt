package com.example.swapicompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.swapicompose.CharacterData
import com.example.swapicompose.Type
import com.example.swapicompose.util.CharacterDataUtil.TestCharacterData.testdata

/*
@Preview(showBackground = true)
@Composable
fun DetailFragmentPreview(){
    CharacterDetail(testdata.name)
}

 */

@Composable
fun CharacterDetail(characterData: String,navController: NavHostController){
    Column(Modifier.fillMaxHeight()) {
        Text("${characterData} моё имя")

        Button(onClick = {navController.popBackStack()}) {
            Text(text = "Back")
        }
    }
}