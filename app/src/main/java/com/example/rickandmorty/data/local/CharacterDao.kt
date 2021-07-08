package com.example.rickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.TABLE_NAME_CHARACTER
import com.example.rickandmorty.data.entities.Character

@Dao
interface CharacterDao {

    @Query("SELECT * FROM $TABLE_NAME_CHARACTER")
    fun getPagingSource(): PagingSource<Int, Character>

    @Query("SELECT * FROM $TABLE_NAME_CHARACTER WHERE id = :id")
    suspend fun getCharacter(id: Int): Character

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Character>)

}