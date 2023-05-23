package com.example.data.di

import android.content.Context
import com.example.data.service.api.MyApiService
import com.example.data.repositoryImpl.GetSoundInfRepositoryImpl
import com.example.data.repositoryImpl.MediaRepositoryImplRepository
import com.example.data.repositoryImpl.SongRepositoryImpl
import com.example.domain.repository.GetSoundInfRepository
import com.example.domain.repository.MediaServiceHandlerRepository
import com.example.domain.repository.SongRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSoundRepository(
        apiService: MyApiService
    ): GetSoundInfRepository = GetSoundInfRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideSoundResultRepository(
        apiService: MyApiService
    ): SongRepository = SongRepositoryImpl(apiService)

    @Singleton
    @Provides
    fun provideMediaServiceHandler(
        @ApplicationContext context: Context
    ): MediaServiceHandlerRepository {
        return MediaRepositoryImplRepository(context)
    }
}