package com.example.smstotelegram.utils.extensions


/**
 * Created by Cuneyt AYYILDIZ on 4/11/2021.
 */
inline fun <reified R> R?.orElse(block: () -> R): R = this ?: block()