package com.example.swapicompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bottomnavbardemo.BottomBarScreen
import com.example.swapicompose.screens.CharacterDetail
import com.example.swapicompose.util.CharacterDataUtil.TestCharacterData.testdata


@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomBarScreen.Search.route) {
        composable(route = BottomBarScreen.Detail.route) {
            CharacterDetail(testdata) }
    }
}

