package com.example.data.di

import com.example.data.service.api.MyApiService
import com.example.data.repositoryImpl.GetSoundInfRepositoryImpl
import com.example.data.repositoryImpl.SongRepositoryImpl
import com.example.domain.repository.GetSoundInfRepository
import com.example.domain.repository.SongRepository
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
    fun provideSoundRepository(apiService: MyApiService): GetSoundInfRepository = GetSoundInfRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideSoundResultRepository(apiService: MyApiService): SongRepository = SongRepositoryImpl(apiService)

}
