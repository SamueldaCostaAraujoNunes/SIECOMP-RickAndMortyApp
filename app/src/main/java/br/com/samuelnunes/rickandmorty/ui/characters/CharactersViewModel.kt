package br.com.samuelnunes.rickandmorty.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.samuelnunes.rickandmorty.data.entities.Character
import br.com.samuelnunes.rickandmorty.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class CharactersViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {
    fun characters(search: String): LiveData<PagingData<Character>> =
        characterRepository.getCharacters(search).cachedIn(viewModelScope).asLiveData()

}
