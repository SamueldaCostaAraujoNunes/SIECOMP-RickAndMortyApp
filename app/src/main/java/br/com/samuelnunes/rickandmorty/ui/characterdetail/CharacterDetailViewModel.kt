package br.com.samuelnunes.rickandmorty.ui.characterdetail

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import br.com.samuelnunes.rickandmorty.data.entities.Character
import br.com.samuelnunes.rickandmorty.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    suspend fun character(id: Int): Character = repository.getCharacter(id)
}
