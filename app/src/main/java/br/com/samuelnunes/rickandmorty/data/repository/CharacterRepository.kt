package br.com.samuelnunes.rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.samuelnunes.rickandmorty.data.entities.Character
import br.com.samuelnunes.rickandmorty.data.local.CharacterDao
import br.com.samuelnunes.rickandmorty.data.remote.CharacterRemoteMediador
import br.com.samuelnunes.rickandmorty.data.remote.CharacterService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRepository @Inject constructor(
    private val service: CharacterService,
    private val dao: CharacterDao
) {

    fun getCharacters(query: String): Flow<PagingData<Character>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { dao.getPagingSource("%$query%") },
        remoteMediator = CharacterRemoteMediador(query, dao, service)
    ).flow

    suspend fun getCharacter(id: Int) = dao.getCharacter(id)

}