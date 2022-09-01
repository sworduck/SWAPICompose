package com.example.swapicompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.bottomnavbardemo.Screens
import com.example.swapicompose.*
import com.example.swapicompose.R

/*
@Preview(showBackground = true)
@Composable
fun SearchFragmentPreview(){
    SearchScreen()
}

 */

@Composable
fun SearchScreen(navController: NavHostController){

    Column(Modifier.fillMaxHeight()) {
        SearchBar(Modifier.fillMaxWidth())
        LazyColumnList(
            listOf(
                CharacterData(1, "name1", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(2, "name2", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(3, "name1", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(4, "name2", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(5, "name1", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(6, "name2", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(7, "name1", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(8, "name2", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(9, "name1", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(10, "name2", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(11, "name1", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(12, "name2", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(13, "name1", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(14, "name2", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(15, "name1", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(16, "name2", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(17, "name1", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(18, "name2", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(19, "name1", "1", "1", "1", "1", Type.DEFAULT),
                CharacterData(20, "name2", "1", "1", "1", "1", Type.DEFAULT),
            ), Modifier.fillMaxHeight(),navController
        )
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

//@Preview()
@Composable
fun SearchBarPreview() {
    SearchBar(
        Modifier.fillMaxWidth()
    )
}

//@Preview(showBackground = true)
/*
@Composable
fun BottomNavigationPreview() {
    BottomNavigation(
        Modifier.fillMaxWidth()
    )
}

 */

/*
@Preview(showBackground = true)
@Composable
fun LazyColumnListPreview() {
    LazyColumnList(
        listOf(
            CharacterData(1, "name1", "1", "1", "1", "1", Type.DEFAULT),
            CharacterData(2, "name2", "1", "1", "1", "1", Type.DEFAULT)
        ),Modifier.fillMaxHeight()
    )
}

 */

@Composable
fun ColumnItem(characterData: CharacterData,navController:NavHostController) {
    var expanded by remember { mutableStateOf(false) }
    val a = "4"
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)) {
        Button({ navController.navigate("${Screens.Detail.route}/$a")
//        {
//            popUpTo(navController.graph.findStartDestination().id)
//            launchSingleTop = true
//        }
               },
            modifier = Modifier.fillMaxWidth(0.9f)) {
            Text(text = if(expanded) characterData.name else "alo")
        }
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth(1f)
        ) {
            Icon(                                                       //заменить на OutLine.Sta
                imageVector = if (expanded) Icons.Filled.Favorite else Icons.Filled.Clear,
                contentDescription = if (expanded) stringResource(R.string.favorite_button)
                else stringResource(R.string.unfavorite_button)
            )
        }
    }
}

@Composable
fun LazyColumnList(messages: List<CharacterData>,modifier: Modifier,navController:NavHostController) {
    LazyColumn(modifier = modifier) {
        items(items = messages) { message ->
            ColumnItem(message,navController)
        }
    }
}