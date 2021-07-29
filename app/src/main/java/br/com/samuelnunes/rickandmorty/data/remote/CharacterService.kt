package br.com.samuelnunes.rickandmorty.data.remote

import br.com.samuelnunes.rickandmorty.data.entities.CharacterList
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {
    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null
    ): CharacterList
}