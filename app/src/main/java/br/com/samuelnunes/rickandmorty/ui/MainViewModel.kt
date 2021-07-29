package br.com.samuelnunes.rickandmorty.ui

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Samuel da Costa Araujo Nunes
 * Created 24/07/2021 at 13:13
 */
class MainViewModel :
    ViewModel() {
    private val _query = MutableLiveData<String>()
    val query = _query
    val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (query != null) {
                _query.postValue(query.lowercase())
            }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean = false
    }
    val onCloseListener = SearchView.OnCloseListener {
        _query.postValue("")
        false
    }


    private val _searchView = MutableLiveData(true)
    val searchViewVisibility: LiveData<Boolean> = _searchView
    fun showSearchView() {
        _searchView.value = true
    }

    fun hideSearchView() {
        _searchView.value = false
    }
}