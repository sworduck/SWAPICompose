package com.example.bottomnavbardemo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swapicompose.screens.CharacterDetail
import com.example.swapicompose.screens.FavoriteScreen
import com.example.swapicompose.screens.SearchScreen
import com.example.swapicompose.util.CharacterDataUtil

@Composable
fun BottomNavGraph(navController: NavHostController) {
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
        composable(route = Screens.Detail.route) {
            CharacterDetail(CharacterDataUtil.testdata.name,navController)
        }
    }
}