package com.example.rickandmorty.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmorty.TABLE_NAME_CHARACTER

@Entity(tableName = TABLE_NAME_CHARACTER)
data class Character(
    val created: String,
    val gender: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)