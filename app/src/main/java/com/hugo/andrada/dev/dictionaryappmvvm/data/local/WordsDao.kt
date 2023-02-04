package com.hugo.andrada.dev.dictionaryappmvvm.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hugo.andrada.dev.dictionaryappmvvm.data.local.entity.WordInfoEntity

@Dao
interface WordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<WordInfoEntity>)

    @Query("DELETE FROM words_table WHERE word IN(:words)")
    suspend fun deleteWords(words: List<String>)

    @Query("SELECT * FROM words_table WHERE word LIKE '%' || :word || '%'")
    suspend fun getWords(word: String): List<WordInfoEntity>
}