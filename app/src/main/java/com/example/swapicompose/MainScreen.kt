package com.example.swapicompose

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavbardemo.Screens
import com.example.bottomnavbardemo.BottomNavGraph
import com.example.swapicompose.screens.CharacterDetail
import com.example.swapicompose.screens.FavoriteScreen
import com.example.swapicompose.screens.SearchScreen
import com.example.swapicompose.util.CharacterDataUtil.TestCharacterData.testdata


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Search.route) {
        composable(Screens.Detail.route) { CharacterDetail(testdata.name,navController) }
        composable(Screens.Favorite.route) { FavoriteScreen(navController) }
        composable(Screens.Search.route) { SearchScreen(navController) }
    }

    Scaffold(
        bottomBar = { BottomBar(navController = navController, listOf(Screens.Search,Screens.Favorite)) }
    ) {
        BottomNavGraph(navController = navController)
    }

    //SearchScreen(navController)

}

@Composable
fun BottomBar(navController: NavHostController, screens: List<Screens>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: Screens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun BottomNavigation(modifier: Modifier = Modifier) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_search))
            },
            selected = true,
            onClick = {}
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_favorite))
            },
            selected = false,
            onClick = {}
        )
    }
}













