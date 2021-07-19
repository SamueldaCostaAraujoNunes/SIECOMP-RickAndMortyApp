package br.com.samuelnunes.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.samuelnunes.rickandmorty.data.entities.Character

@Database(entities = [Character::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}