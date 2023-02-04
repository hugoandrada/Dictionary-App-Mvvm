package com.hugo.andrada.dev.dictionaryappmvvm.data.injection

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.hugo.andrada.dev.dictionaryappmvvm.data.local.Converters
import com.hugo.andrada.dev.dictionaryappmvvm.data.local.WordsDatabase
import com.hugo.andrada.dev.dictionaryappmvvm.data.remote.DictionaryApi
import com.hugo.andrada.dev.dictionaryappmvvm.data.repository.RepositoryImpl
import com.hugo.andrada.dev.dictionaryappmvvm.data.util.GsonParser
import com.hugo.andrada.dev.dictionaryappmvvm.domain.repository.Repository
import com.hugo.andrada.dev.dictionaryappmvvm.domain.use_cases.GetWordsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordsModule {

    @Provides
    @Singleton
    fun provideRepository(
        db: WordsDatabase,
        api: DictionaryApi
    ): Repository {
        return RepositoryImpl(api,db.wordsDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): WordsDatabase {
        return Room.databaseBuilder(
            app, WordsDatabase::class.java, "words_database"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): GetWordsUseCase {
        return GetWordsUseCase(repository)
    }
}