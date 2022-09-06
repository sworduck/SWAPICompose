package com.example.swapicompose.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.swapicompose.data.cache.character.CharacterDataBaseDao
import com.example.swapicompose.data.cache.character.CharacterDataBaseEntity
import com.example.swapicompose.data.cache.character.Converter
import com.example.swapicompose.data.cache.film.FilmDataBaseDao
import com.example.swapicompose.data.cache.film.FilmDataBaseEntity

@Database(entities = [CharacterDataBaseEntity::class, FilmDataBaseEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converter::class)
abstract class SwapiRoomDataBase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "notes_db"
    }

    abstract fun characterDataBaseDao(): CharacterDataBaseDao
    abstract fun filmDataBaseDao(): FilmDataBaseDao
}