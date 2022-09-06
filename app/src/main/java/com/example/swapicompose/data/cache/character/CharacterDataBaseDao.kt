package com.example.swapicompose.data.cache.character

import androidx.room.*
import com.example.swapicompose.utilis.Type

@Dao
interface CharacterDataBaseDao {
    @Query("SELECT * FROM CharacterDataBaseEntity")
    fun getAllCharacter(): List<CharacterDataBaseEntity>

    @Query("SELECT * FROM CharacterDataBaseEntity WHERE idOnPage=:id")
    fun getCharacter(id: Int): CharacterDataBaseEntity

    @Query("SELECT * FROM CharacterDataBaseEntity")
    fun getCharacterList(): List<CharacterDataBaseEntity>

    @Query("SELECT * FROM CharacterDataBaseEntity WHERE type LIKE :type AND idOnPage BETWEEN :page*10 AND :page*10+9")
    fun checkDataFromDB(type: Type, page: Int): List<CharacterDataBaseEntity>

    @Query("SELECT * FROM CharacterDataBaseEntity WHERE type LIKE :type")
    fun getCharacterListByType(type: Type): List<CharacterDataBaseEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(characterDataBaseEntity: CharacterDataBaseEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(characterDataBaseEntityList: List<CharacterDataBaseEntity>)

    @Update
    fun update(characterDataBaseEntity: CharacterDataBaseEntity)

    @Query("DELETE FROM CharacterDataBaseEntity WHERE idOnPage = :id")
    fun delete(id: Int)

    @Query("DELETE FROM CharacterDataBaseEntity")
    fun deleteAll()

    @Query("DELETE FROM CharacterDataBaseEntity WHERE type = :type")
    suspend fun deleteType(type: Type)
}