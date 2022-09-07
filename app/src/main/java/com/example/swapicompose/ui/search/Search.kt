package com.example.swapicompose.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bottomnavbardemo.Screens
import com.example.swapicompose.R
import com.example.swapicompose.data.CharacterData
import com.example.swapicompose.utilis.Type
import java.util.*

@Composable
fun SearchScreen(
    navController: NavHostController,
    vm: SearchViewModel = viewModel(),
) {
    vm.viewCreated()
    val users by vm.characterDataList.collectAsState()
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val isLoading by vm.isLoading.collectAsState()
    val error by vm.errorMessage.collectAsState()

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

    fun onClickRetry() {
        vm.retryClicked()
    }
    when (error) {
        "" -> {
            when {
                isLoading -> {
                    LoadingUi()
                }
                else -> {
                    Column(Modifier.fillMaxHeight()) {
                        SearchBar(textState, Modifier.fillMaxWidth())
                        CharacterList(
                            textState,
                            users,
                            Modifier.fillMaxHeight(),
                            navController,
                            ::onClickFavorite
                        )
                    }
                }
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(text = error)
                    Button(onClick = {
                        onClickRetry()
                    }, Modifier.align(Alignment.CenterHorizontally)) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingUi() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}


@Composable
fun SearchBar(
    state: MutableState<TextFieldValue>,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
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

@Composable
fun CharacterListItem(
    characterData: CharacterData,
    navController: NavHostController,
    onClickFavorite: (CharacterData) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    expanded = characterData.type == Type.FAVORITE
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)) {
        Button({
            navController.navigate("${Screens.Detail.route}/${characterData.id}")
        },
            modifier = Modifier.fillMaxWidth(0.9f)) {
            Text(text = characterData.name)
        }
        IconButton(
            onClick = {
                onClickFavorite(characterData)
                expanded = !expanded
            },
            modifier = Modifier.fillMaxWidth(1f)
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

@Composable
fun CharacterList(
    state: MutableState<TextFieldValue>,
    messages: List<CharacterData>,
    modifier: Modifier,
    navController: NavHostController,
    onClickFavorite: (CharacterData) -> Unit,
) {
    var filteredMessages: List<CharacterData>
    LazyColumn(modifier = modifier) {
        val searchedText = state.value.text
        filteredMessages = if (searchedText.isEmpty()) {
            messages
        } else {
            val resultList = ArrayList<CharacterData>()
            for (country in messages) {
                if (country.name.lowercase(Locale.getDefault())
                        .contains(searchedText.lowercase(Locale.getDefault()))
                ) {
                    resultList.add(country)
                }
            }
            resultList
        }
        items(items = filteredMessages) { message ->
            CharacterListItem(message, navController, onClickFavorite)
        }
    }
}