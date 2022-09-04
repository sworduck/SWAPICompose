package com.example.swapicompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bottomnavbardemo.BottomNavGraph
import com.example.bottomnavbardemo.Screens
import com.example.swapicompose.screens.CharacterDetail
import com.example.swapicompose.screens.FavoriteScreen
import com.example.swapicompose.screens.SearchScreen
import com.example.swapicompose.util.CharacterDataUtil.TestCharacterData.testdata


@Composable
fun MainScreen() {

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        Screens.Search.route -> {
            bottomBarState.value = true
        }
        Screens.Favorite.route -> {
            bottomBarState.value = true
        }
        "${Screens.Detail.route}/{userId}" -> {
            bottomBarState.value = false
        }
    }

    //BottomNavGraph(navController = navController)
    /*
    NavHost(navController = navController, startDestination = Screens.Search.route) {
        composable(Screens.Detail.route) { CharacterDetail(navController,testdata.name) }
        composable(Screens.Favorite.route) { FavoriteScreen(navController) }
        composable(Screens.Search.route) { SearchScreen(navController) }
    }

     */

    com.google.accompanist.insets.ui.Scaffold(
        bottomBar = { BottomBar(navController = navController, listOf(Screens.Search,Screens.Favorite),bottomBarState) },
        content = {
            NavHost(
                navController = navController,
                startDestination = Screens.Search.route
            ) {
                composable(route = Screens.Search.route) {
                    SearchScreen(navController)
                }
                composable(route = Screens.Favorite.route) {
                    FavoriteScreen(navController)
                }
                composable(route = "${Screens.Detail.route}/{userId}") {
                    CharacterDetail(navController,it.arguments?.getString("userId") ?: "1")
                }
            }
        })

    //SearchScreen(navController)

}

@Composable
fun BottomBar(navController: NavHostController, screens: List<Screens>,bottomBarState:MutableState<Boolean>) {

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
                    selected = currentDestination==screen.route,
                    onClick = {
                        navController.navigate(screen.route){
                            popUpTo(navController.graph.findStartDestination().id){
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













