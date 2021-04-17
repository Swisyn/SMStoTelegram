package com.cuneytayyildiz.smstotelegram.di

import com.cuneytayyildiz.smstotelegram.data.CallRepository
import com.cuneytayyildiz.smstotelegram.data.CallRepositoryImpl
import com.cuneytayyildiz.smstotelegram.data.SmsRepository
import com.cuneytayyildiz.smstotelegram.data.SmsRepositoryImpl
import com.cuneytayyildiz.smstotelegram.data.local.AppPreferences
import com.cuneytayyildiz.smstotelegram.data.remote.TelegramApi
import com.cuneytayyildiz.smstotelegram.di.annotations.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */


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