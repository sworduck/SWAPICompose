package com.example.bottomnavbardemo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Search : Screens(
        route = "search",
        title = "Search",
        icon = Icons.Default.Search
    )

    object Favorite : Screens(
        route = "favorite",
        title = "Favorite",
        icon = Icons.Default.Favorite
    )

    object Detail : Screens(
        route = "detail",
        title = "Detail",
        icon = Icons.Default.Favorite
    )
}
