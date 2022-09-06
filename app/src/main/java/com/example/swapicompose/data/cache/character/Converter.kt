package com.example.swapicompose.data.cache.character

import androidx.room.TypeConverter
import com.example.swapicompose.utilis.Type

class Converter {
    @TypeConverter
    fun fromPriority(type: Type): String {
        return type.name
    }

    @TypeConverter
    fun toPriority(type: String): Type {
        return Type.valueOf(type)
    }

}