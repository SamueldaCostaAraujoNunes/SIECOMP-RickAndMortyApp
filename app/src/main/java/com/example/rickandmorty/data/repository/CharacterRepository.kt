package com.example.rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.local.CharacterDao
import com.example.rickandmorty.data.remote.CharacterRemoteMediador
import com.example.rickandmorty.data.remote.CharacterService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRepository @Inject constructor(
    private val service: CharacterService,
    private val database: CharacterDao
) {
    fun getCharacters(): Flow<PagingData<Character>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { database.getPagingSource() },
        remoteMediator = CharacterRemoteMediador(database, service)
    ).flow

    suspend fun getCharacter(id: Int) = database.getCharacter(id)

}