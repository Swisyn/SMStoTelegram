package com.cuneytayyildiz.smstotelegram.di.annotations

import javax.inject.Qualifier


/**
 * Created by Cuneyt AYYILDIZ on 4/14/2021.
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher