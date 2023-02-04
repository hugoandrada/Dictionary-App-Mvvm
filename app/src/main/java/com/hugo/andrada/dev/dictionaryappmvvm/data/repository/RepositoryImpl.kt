package com.hugo.andrada.dev.dictionaryappmvvm.data.repository

import com.hugo.andrada.dev.dictionaryappmvvm.core.ResultState
import com.hugo.andrada.dev.dictionaryappmvvm.data.local.WordsDao
import com.hugo.andrada.dev.dictionaryappmvvm.data.remote.DictionaryApi
import com.hugo.andrada.dev.dictionaryappmvvm.domain.model.WordInfo
import com.hugo.andrada.dev.dictionaryappmvvm.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: DictionaryApi,
    private val dao: WordsDao
): Repository {

    override fun getWords(word: String): Flow<ResultState<List<WordInfo>>> = flow {

        emit(ResultState.Loading())

        val words = dao.getWords(word).map { it.toWordInfo() }
        emit(ResultState.Loading(data = words))

        try {
            val remoteWords = api.getWords(word)
            dao.deleteWords(remoteWords.map { it.word })
            dao.insertWords(remoteWords.map { it.toWordInfoEntity() })

        } catch (e: IOException) {
            emit(ResultState.Error(
                message = "server error",
                data = words
            ))
        } catch (e: HttpException) {
            emit(ResultState.Error(
                message = "something went wrong",
                data = words
            ))
        }

        val newWords = dao.getWords(word).map { it.toWordInfo() }
        emit(ResultState.Success(data = newWords))
    }
}