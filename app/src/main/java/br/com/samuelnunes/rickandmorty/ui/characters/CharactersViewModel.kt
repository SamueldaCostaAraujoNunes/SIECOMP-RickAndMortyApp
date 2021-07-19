package br.com.samuelnunes.rickandmorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import br.com.samuelnunes.rickandmorty.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class CharactersViewModel @Inject constructor(characterRepository: CharacterRepository) :
    ViewModel() {
    val characters = characterRepository.getCharacters().cachedIn(viewModelScope).asLiveData()
}