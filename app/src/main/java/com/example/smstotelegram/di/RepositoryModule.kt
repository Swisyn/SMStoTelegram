package com.example.smstotelegram.di

import com.example.smstotelegram.data.CallRepository
import com.example.smstotelegram.data.CallRepositoryImpl
import com.example.smstotelegram.data.SmsRepository
import com.example.smstotelegram.data.SmsRepositoryImpl
import com.example.smstotelegram.data.local.AppPreferences
import com.example.smstotelegram.data.remote.TelegramApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineScope {
        return CoroutineScope(Dispatchers.Main)
    }

    @Provides
    @Singleton
    fun providesSmsRepository(
        telegramApi: TelegramApi,
        appPreferences: AppPreferences
    ): SmsRepository {
        return SmsRepositoryImpl(telegramApi = telegramApi, appPreferences = appPreferences)
    }

    @Provides
    @Singleton
    fun providesCallRepository(
        telegramApi: TelegramApi,
        appPreferences: AppPreferences
    ): CallRepository {
        return CallRepositoryImpl(telegramApi = telegramApi, appPreferences = appPreferences)
    }
}