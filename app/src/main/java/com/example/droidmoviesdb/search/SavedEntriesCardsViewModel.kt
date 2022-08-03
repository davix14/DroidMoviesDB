package com.example.droidmoviesdb.search

import SingleSearchResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SavedEntriesCardsViewModel : ViewModel() {

    private val _cards = MutableStateFlow(listOf<ExpandableCardModel>())
    val cards: StateFlow<List<ExpandableCardModel>> get() = _cards

    private val _expandedCardIdsList = MutableStateFlow(listOf<Int>())
    val expandedCardIdsList: StateFlow<List<Int>> get() = _expandedCardIdsList

    init {
        getFakeData()
    }

    private val sampleResults = listOf<SingleSearchResult>(
        SingleSearchResult(
            "Split",
            "2016",
            "tt4972582",
            "movie",
            "https://m.media-amazon.com/images/M/MV5BZTJiNGM2NjItNDRiYy00ZjY0LTgwNTItZDBmZGRlODQ4YThkL2ltYWdlXkEyXkFqcGdeQXVyMjY5ODI4NDk@._V1_SX300.jpg"
        ),
        SingleSearchResult(
            "Split Second",
            "1992",
            "tt0105459",
            "movie",
            "https://m.media-amazon.com/images/M/MV5BY2I2ZjBmMWEtMTVkZC00OTVkLWE1MjktOTc0MDgwNzZhYmU3XkEyXkFqcGdeQXVyNjExODE1MDc@._V1_SX300.jpg"
        ),
        SingleSearchResult(
            "California Split",
            "1974",
            "tt0071269",
            "movie",
            "https://m.media-amazon.com/images/M/MV5BNjk5MzNjMDctYTdhNS00OWI0LWI2MzUtMDI3ODYwMDc5M2E3XkEyXkFqcGdeQXVyMjI4MjA5MzA@._V1_SX300.jpg"
        ),
        SingleSearchResult(
            "The Split",
            "2018–2022",
            "tt7631146",
            "series",
            "https://m.media-amazon.com/images/M/MV5BODhhNzhmNDYtMTQyYy00NjMzLWJkNTMtZDc1YjZmZDE4NTM4XkEyXkFqcGdeQXVyMTM1MTE1NDMx._V1_SX300.jpg"
        ),
        SingleSearchResult(
            "Banana Split",
            "2018",
            "tt7755856",
            "movie",
            "https://m.media-amazon.com/images/M/MV5BYTlkNWRjMDMtYmVlYS00NTlkLWI5OTUtZWY1YmZmNTNkZGFjXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg"
        )
    )

    private fun getFakeData() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val testList = arrayListOf<ExpandableCardModel>()
                for ((index, value) in sampleResults.withIndex()) {
                    testList.add(ExpandableCardModel(id = index, savedEntry = value))
                }
                _cards.emit(testList)
            }
        }
    }

    fun onCardArrowClicked(cardId: Int) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }
}