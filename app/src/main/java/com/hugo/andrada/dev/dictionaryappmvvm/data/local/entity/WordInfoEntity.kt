package com.hugo.andrada.dev.dictionaryappmvvm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hugo.andrada.dev.dictionaryappmvvm.domain.model.Meaning
import com.hugo.andrada.dev.dictionaryappmvvm.domain.model.WordInfo

@Entity(tableName = "words_table")
data class WordInfoEntity(
    val word: String,
    val phonetic: String,
    val meanings: List<Meaning>,
    @PrimaryKey val id: Int? = null
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            word = word,
            phonetic = phonetic
        )
    }
}