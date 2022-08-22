package com.example.bottomnavbardemo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Search : BottomBarScreen(
        route = "search",
        title = "Search",
        icon = Icons.Default.Search
    )

    object Favorite : BottomBarScreen(
        route = "favorite",
        title = "Favorite",
        icon = Icons.Default.Favorite
    )

    object Detail : BottomBarScreen(
        route = "detail",
        title = "Detail",
        icon = Icons.Default.Favorite
    )
}
