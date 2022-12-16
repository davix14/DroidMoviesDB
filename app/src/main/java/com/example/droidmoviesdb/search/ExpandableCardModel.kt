package com.example.droidmoviesdb.search

import com.example.droidmoviesdb.domain.models.SingleSearchResult
import androidx.compose.runtime.Immutable

@Immutable
data class ExpandableCardModel(val id: Int, val savedEntry: SingleSearchResult)