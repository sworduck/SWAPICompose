package com.example.swapicompose.data

interface SearchRepository {
    suspend fun fetchCharacterList(): List<CharacterData>
}