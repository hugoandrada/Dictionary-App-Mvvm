package com.hugo.andrada.dev.dictionaryappmvvm.data.remote.dto

import com.hugo.andrada.dev.dictionaryappmvvm.domain.model.Meaning

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String
){
    fun toMeaning(): Meaning {
        return Meaning(
            definitions = definitions.map { it.toDefinition() },
            partOfSpeech = partOfSpeech
        )
    }
}