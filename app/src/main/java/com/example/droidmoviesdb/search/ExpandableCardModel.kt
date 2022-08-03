package com.example.droidmoviesdb.search

import SingleSearchResult
import androidx.compose.runtime.Immutable

@Immutable
data class ExpandableCardModel(val id: Int, val savedEntry: SingleSearchResult)