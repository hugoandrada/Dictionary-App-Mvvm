package com.hugo.andrada.dev.dictionaryappmvvm.domain.use_cases

import com.hugo.andrada.dev.dictionaryappmvvm.core.ResultState
import com.hugo.andrada.dev.dictionaryappmvvm.domain.model.WordInfo
import com.hugo.andrada.dev.dictionaryappmvvm.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(word: String): Flow<ResultState<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow {  }
        }
        return repository.getWords(word)
    }
}