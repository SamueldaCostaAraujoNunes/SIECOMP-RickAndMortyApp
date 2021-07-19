package br.com.samuelnunes.rickandmorty.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import br.com.samuelnunes.rickandmorty.TABLE_NAME_CHARACTER
import br.com.samuelnunes.rickandmorty.data.local.AppDatabase
import br.com.samuelnunes.rickandmorty.data.local.CharacterDao
import br.com.samuelnunes.rickandmorty.data.remote.CharacterService
import br.com.samuelnunes.rickandmorty.data.repository.CharacterRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, TABLE_NAME_CHARACTER)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase): CharacterDao = db.characterDao()

    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun provideRepository(
        service: CharacterService,
        localDataSource: CharacterDao
    ): CharacterRepository = CharacterRepository(service, localDataSource)

}