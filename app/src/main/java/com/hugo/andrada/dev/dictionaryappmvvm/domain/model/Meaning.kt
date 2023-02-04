package com.hugo.andrada.dev.dictionaryappmvvm.domain.model

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)