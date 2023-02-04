package com.hugo.andrada.dev.dictionaryappmvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hugo.andrada.dev.dictionaryappmvvm.data.local.entity.WordInfoEntity

@Database(
    entities = [WordInfoEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class WordsDatabase: RoomDatabase() {

    abstract val wordsDao: WordsDao
}