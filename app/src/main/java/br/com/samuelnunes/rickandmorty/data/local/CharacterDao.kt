package br.com.samuelnunes.rickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.samuelnunes.rickandmorty.data.entities.Character

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Character WHERE name LIKE :query")
    fun getPagingSource(query: String): PagingSource<Int, Character>

    @Query("SELECT * FROM Character WHERE id = :id")
    suspend fun getCharacter(id: Int): Character

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Character>)

}