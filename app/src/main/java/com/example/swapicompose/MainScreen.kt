package com.example.swapicompose

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.swapicompose.ui.detail.CharacterDetail
import com.example.swapicompose.ui.detail.CharacterDetailTest
import com.example.swapicompose.ui.favorite.FavoriteScreen
import com.example.swapicompose.ui.favorite.FavoriteScreenTest
import com.example.swapicompose.ui.search.SearchScreen
import com.example.swapicompose.ui.search.SearchScreenTest
import com.example.swapicompose.ui.theme.SWAPIComposeTheme
import com.example.swapicompose.utilis.CharacterDataUtil.TestCharacterData.testdata

//тут Detail в bottombar чтобы не передавать navController
//тестовым представлениям
@Preview(showBackground = true,uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MainScreenPreviewLight() {
    SWAPIComposeTheme {
        MainScreenTest()
    }
}

@Preview(showBackground = true,uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreviewNight() {
    SWAPIComposeTheme {
        MainScreenTest()
    }
}

@Composable
fun MainScreen() {

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        Screens.Search.route -> {
            bottomBarState.value = true
        }
        Screens.Favorite.route -> {
            bottomBarState.value = true
        }
        "${Screens.Detail.route}/{characterId}" -> {
            bottomBarState.value = false
        }
    }

    com.google.accompanist.insets.ui.Scaffold(
        bottomBar = {
            BottomBar(navController = navController,
                listOf(Screens.Search, Screens.Favorite),
                bottomBarState)
        },
        content = {
            NavHost(
                navController = navController,
                startDestination = Screens.Search.route
            ) {
                composable(route = Screens.Search.route) {
                    SearchScreen(navController, vm = hiltViewModel())
                }
                composable(route = Screens.Favorite.route) {
                    FavoriteScreen(navController, vm = hiltViewModel())
                }
                composable(route = "${Screens.Detail.route}/{characterId}") {
                    CharacterDetail(navController, it.arguments?.getString("characterId") ?: "1",vm = hiltViewModel())
                }
            }
        })
}

@Composable
fun BottomBar(
    navController: NavHostController,
    screens: List<Screens>,
    bottomBarState: MutableState<Boolean>,
) {

    AnimatedVisibility(visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {

            BottomNavigation {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route

                screens.forEach { screen ->

                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = "Navigation Icon"
                            )
                        },
                        label = {
                            Text(text = screen.title)
                        },
                        selected = currentDestination == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        })
}

@Composable
fun MainScreenTest() {

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val navController = rememberNavController()

    com.google.accompanist.insets.ui.Scaffold(
        bottomBar = {
            //тут Detail в bottombar чтобы не передавать navController
            //тестовым представлениям
            BottomBar(navController = navController,
                listOf(Screens.Search, Screens.Favorite,Screens.Detail),
                bottomBarState)
        },
        content = {
            NavHost(
                navController = navController,
                startDestination = Screens.Search.route
            ) {
                composable(route = Screens.Search.route) {
                    SearchScreenTest()
                }
                composable(route = Screens.Favorite.route) {
                    FavoriteScreenTest()
                }
                composable(route = Screens.Detail.route) {
                    CharacterDetailTest(testdata)
                }
            }
        })
}












