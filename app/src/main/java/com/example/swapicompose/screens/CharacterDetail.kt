package com.example.swapicompose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.swapicompose.CharacterData
import com.example.swapicompose.Type
import com.example.swapicompose.util.CharacterDataUtil.TestCharacterData.testdata

@Preview(showBackground = true)
@Composable
fun DetailFragmentPreview(){
    CharacterDetail(testdata)
}

@Composable
fun CharacterDetail(characterData: CharacterData){
    Column(Modifier.fillMaxHeight()) {
        Text("${characterData.name} моё имя")

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Back")
        }
    }
}