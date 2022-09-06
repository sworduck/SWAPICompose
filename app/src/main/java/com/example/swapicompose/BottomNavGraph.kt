package com.example.bottomnavbardemo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.swapicompose.screens.CharacterDetail
import com.example.swapicompose.screens.FavoriteScreen
import com.example.swapicompose.screens.SearchScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Search.route
    ) {
        composable(route = Screens.Search.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })) {
            SearchScreen(navController)
        }
        composable(route = Screens.Favorite.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })) {
            FavoriteScreen(navController)
        }
        composable(route = "${Screens.Detail.route}/{userId}") {
            CharacterDetail(navController,it.arguments?.getString("userId") ?: "1")
        }
    }
}