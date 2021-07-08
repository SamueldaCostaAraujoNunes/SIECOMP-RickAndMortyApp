package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.entities.CharacterList
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {
    @GET("character")
    suspend fun getAllCharacters(@Query("page") page: Int = 1): CharacterList

//    @GET("character/{id}")
//    suspend fun getCharacter(@Path("id") id: Int): Character
}