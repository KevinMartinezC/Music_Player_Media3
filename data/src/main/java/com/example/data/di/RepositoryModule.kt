package com.example.data.di

import com.example.data.MyApiService
import com.example.data.repositoryImpl.SoundRepositoryImpl
import com.example.domain.repository.SoundRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSoundRepository(apiService: MyApiService): SoundRepository = SoundRepositoryImpl(apiService)
}
