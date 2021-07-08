package com.example.rickandmorty.ui.characterdetail

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    suspend fun character(id: Int): Character = repository.getCharacter(id)
}
