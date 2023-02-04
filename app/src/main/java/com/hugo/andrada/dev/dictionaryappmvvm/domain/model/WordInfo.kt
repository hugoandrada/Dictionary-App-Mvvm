package com.hugo.andrada.dev.dictionaryappmvvm.domain.model

data class WordInfo(
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
)