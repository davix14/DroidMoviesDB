package com.example.droidmoviesdb.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidmoviesdb.domain.RepoFactory
import com.example.droidmoviesdb.domain.models.SearchByMany
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private lateinit var searchIn: String

    var latestResults = MutableLiveData<SearchByMany>()

    init {
        RepoFactory.getSearchRepository().latestResponse.subscribe {
            latestResults.value = it
        }
    }

    fun searchInputValidation(search: String) {
        if (search.isNotEmpty() && search.length >= 2)
            getSearchResults(search)
    }

    private fun getSearchResults(searchIn: String) {
        viewModelScope.launch {
            RepoFactory.getSearchRepository().getSearchResults(searchIn)
        }
    }
}