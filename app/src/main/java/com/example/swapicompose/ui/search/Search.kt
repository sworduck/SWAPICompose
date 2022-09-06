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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.bottomnavbardemo.Screens
import com.example.swapicompose.*
import com.example.swapicompose.R
import com.example.swapicompose.data.CharacterData
import com.example.swapicompose.ui.search.SearchViewModel
import com.example.swapicompose.utilis.Type

/*
@Preview(showBackground = true)
@Composable
fun SearchFragmentPreview(){
    SearchScreen()
}

 */

@Composable
fun SearchScreen(
    navController: NavHostController,
    vm: SearchViewModel = viewModel(),
){
    vm.viewCreated()
    val users = vm.characterDataList.observeAsState()
    fun onClickFavorite(characterData: CharacterData) {
        vm.onClickFavoriteButton(characterData)
    }
    if(users.value?.isNotEmpty() == true) {
        Column(Modifier.fillMaxHeight()) {
            SearchBar(Modifier.fillMaxWidth())
            LazyColumnList(
               vm.characterDataList.value!!, Modifier.fillMaxHeight(), navController,::onClickFavorite
            )
        }
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
fun ColumnItem(characterData: CharacterData,navController:NavHostController, onClickFavorite: (CharacterData)-> Unit) {
    var expanded by remember { mutableStateOf(characterData.type == Type.FAVORITE) }
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
            Text(text = characterData.name)
        }
        IconButton(
            onClick = {
                expanded = !expanded
                onClickFavorite(characterData)
                      },
            modifier = Modifier.fillMaxWidth(1f)
        ) {
            Icon(                                                       //заменить на OutLine.Star
                painter = if (expanded) painterResource(id = R.drawable.ic_baseline_favorite_24)
                else painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                contentDescription = if (expanded) stringResource(R.string.favorite_button)
                else stringResource(R.string.unfavorite_button)
            )
        }
    }
}

@Composable
fun LazyColumnList(messages: List<CharacterData>,modifier: Modifier,navController:NavHostController,onClickFavorite: (CharacterData)-> Unit) {
    LazyColumn(modifier = modifier) {
        items(items = messages) { message ->
            ColumnItem(message,navController,onClickFavorite)
        }
    }
}