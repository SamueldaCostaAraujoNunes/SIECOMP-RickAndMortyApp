package com.example.rickandmorty.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.entities.Character
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * @author Samuel da Costa Araujo Nunes
 * Created 05/07/2021 at 13:18
 */

const val STARTING_PAGE_INDEX = 1

class CharacterPagingSource @Inject constructor(
    private val characterService: CharacterService
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val position: Int = params.key ?: STARTING_PAGE_INDEX
            val result = characterService.getAllCharacters(position)
            val characters = result.results

            LoadResult.Page(
                data = characters,
                prevKey = if (position == STARTING_PAGE_INDEX) null else -1,
                nextKey = if (characters.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            Timber.d(e)
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Timber.d(e)
            LoadResult.Error(e)
        }
    }
}