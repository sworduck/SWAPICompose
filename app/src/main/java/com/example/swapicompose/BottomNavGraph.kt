package com.example.bottomnavbardemo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
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
        startDestination = BottomBarScreen.Search.route
    ) {
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen()
        }
        composable(route = BottomBarScreen.Favorite.route) {
            FavoriteScreen()
        }
        composable(route = BottomBarScreen.Detail.route) {
            CharacterDetail(CharacterDataUtil.testdata)
        }
    }
}