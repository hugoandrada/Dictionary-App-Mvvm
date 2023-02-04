package com.hugo.andrada.dev.dictionaryappmvvm.presentation.home

import com.hugo.andrada.dev.dictionaryappmvvm.domain.model.WordInfo

data class HomeState(
    val words: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)
