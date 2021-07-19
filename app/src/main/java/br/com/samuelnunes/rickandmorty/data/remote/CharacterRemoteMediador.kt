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
    private val dao: CharacterDao,
    private val service: CharacterService
) : RemoteMediator<Int, Character>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        val pageSize = state.config.pageSize
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val i = getRemoteKeyClosestToCurrentPosition(state) ?: 1
                indexToPage(i, pageSize)
            }
            LoadType.PREPEND -> {
                val i = getRemoteKeyForFirstItem(state) ?: 1
                val cap = indexToPage(i, pageSize)
                if (cap == 34) return MediatorResult.Success(endOfPaginationReached = true)
                indexToPage(i, pageSize)
            }
            LoadType.APPEND -> {
                val i = getRemoteKeyForLastItem(state) ?: 1
                indexToPage(i, pageSize)
            }
        }

        return try {
            val response = service.getAllCharacters(page)
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

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Character>): Int? =
        state.anchorPosition?.let { state.closestItemToPosition(it) }?.id

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): Int? =
        state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.id

    private fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): Int? =
        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.id

    private fun indexToPage(position: Int, pageSize: Int = 20): Int {
        return (position / pageSize) + 1
    }
}