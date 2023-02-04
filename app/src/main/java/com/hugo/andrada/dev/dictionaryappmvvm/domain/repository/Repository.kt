package com.hugo.andrada.dev.dictionaryappmvvm.domain.repository

import com.hugo.andrada.dev.dictionaryappmvvm.core.ResultState
import com.hugo.andrada.dev.dictionaryappmvvm.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getWords(word: String): Flow<ResultState<List<WordInfo>>>
}