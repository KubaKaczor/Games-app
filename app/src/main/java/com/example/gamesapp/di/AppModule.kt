package com.example.gamesapp.di

import com.example.gamesapp.network.GamesApi
import com.example.gamesapp.repository.GamesRepository
import com.example.gamesapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideGamesRepository(api: GamesApi): GamesRepository {
        return GamesRepository(api)
    }

    @Singleton
    @Provides
    fun provideGamesApi(): GamesApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GamesApi::class.java)
    }
}