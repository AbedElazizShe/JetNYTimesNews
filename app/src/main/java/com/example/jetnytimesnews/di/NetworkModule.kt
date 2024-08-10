package com.example.jetnytimesnews.di

import com.example.jetnytimesnews.api.NYTimesNewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideNYTimesNewsService(): NYTimesNewsService {
        return NYTimesNewsService.create()
    }
}
