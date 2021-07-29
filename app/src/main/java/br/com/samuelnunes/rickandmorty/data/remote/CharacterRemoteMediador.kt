package br.com.samuelnunes.rickandmorty.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import br.com.samuelnunes.rickandmorty.data.entities.Character
import br.com.samuelnunes.rickandmorty.data.local.CharacterDao
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * @author Samuel da Costa Araujo Nunes
 * Created 05/07/2021 at 14:58
 */

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediador @Inject constructor(
    private val query: String,
    private val dao: CharacterDao,
    private val service: CharacterService
) : RemoteMediator<Int, Character>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        val pageSize = state.config.pageSize
        val page = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                (lastItem.id / pageSize) + 1
            }
        }

        return try {
            val response = service.getAllCharacters(
                page = page,
                name = query
            )
            dao.insertAll(response.results)
            MediatorResult.Success(
                endOfPaginationReached = response.info.next == null
            )
        } catch (e: HttpException) {
            Timber.d(e, "Error during fetch")
            MediatorResult.Error(e)
        } catch (e: IOException) {
            Timber.d(e, "Error during fetch")
            MediatorResult.Error(e)
        }
    }

}