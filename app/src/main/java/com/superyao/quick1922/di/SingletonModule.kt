package com.superyao.quick1922.di

import android.app.Application
import com.superyao.quick1922.data.DataRepository
import com.superyao.quick1922.data.local.LocalData
import com.superyao.quick1922.data.local.SharedPreferencesBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {
    @Provides
    @Singleton
    fun provideSharedPreferencesBase(application: Application): SharedPreferencesBase {
        return SharedPreferencesBase(application)
    }

    @Provides
    @Singleton
    fun provideLocalData(sharedPreferencesBase: SharedPreferencesBase): LocalData {
        return LocalData(sharedPreferencesBase)
    }

    @Provides
    @Singleton
    fun provideDataRepository(localData: LocalData): DataRepository {
        return DataRepository(localData)
    }
}