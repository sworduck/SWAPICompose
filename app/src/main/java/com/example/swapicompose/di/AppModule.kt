package com.example.swapicompose.di

import android.content.Context
import androidx.room.Room
import com.example.swapicompose.data.cache.BaseCacheDataSource
import com.example.swapicompose.data.cache.SwapiRoomDataBase
import com.example.swapicompose.data.cloud.BaseCloudDataSource
import com.example.swapicompose.di.ApiModule.apiService
import com.example.swapicompose.domain.FavoriteUseCase

class AppModule(context: Context) {
    private val db =  Room.databaseBuilder(
        context,
        SwapiRoomDataBase::class.java,
        SwapiRoomDataBase.DATABASE_NAME
    ).build()

    val baseCloudDataSource = BaseCloudDataSource(apiService)
    val baseCacheDataSource = BaseCacheDataSource(db)
    val favoriteUseCase = FavoriteUseCase(baseCacheDataSource)
}