package com.cuneytayyildiz.smstotelegram.di

import android.content.Context
import com.cuneytayyildiz.smstotelegram.BuildConfig
import com.cuneytayyildiz.smstotelegram.data.remote.TelegramApi
import com.cuneytayyildiz.smstotelegram.di.annotations.ConnectionStatus
import com.cuneytayyildiz.smstotelegram.utils.managers.ConnectionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @ConnectionStatus
    @Provides
    @Singleton
    fun providesConnectionStatus(@ApplicationContext context: Context): ConnectionManager {
        return ConnectionManager(context = context)
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TELEGRAM_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesTelegramApi(retrofit: Retrofit): TelegramApi {
        return retrofit.create(TelegramApi::class.java)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}