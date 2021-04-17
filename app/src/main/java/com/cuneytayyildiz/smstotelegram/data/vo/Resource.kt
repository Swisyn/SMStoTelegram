package com.cuneytayyildiz.smstotelegram.data.vo


/**
 * Created by Cuneyt AYYILDIZ on 4/12/2021.
 */
sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val exception: Throwable) : Resource<Nothing>()
}