package com.cuneytayyildiz.smstotelegram.data.remote.base

import com.cuneytayyildiz.smstotelegram.data.vo.Resource
import com.cuneytayyildiz.smstotelegram.utils.extensions.orElse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response


/**
 * Created by Cuneyt AYYILDIZ on 4/12/2021.
 */

suspend inline fun <T : Any> createApiCall(
    noinline call: suspend () -> Response<T>
): Flow<Resource<T>> {
    return flow {
        val apiCallResponse = call.invoke()

        if (apiCallResponse.isSuccessful) {
            apiCallResponse.body()?.let { body ->
                emit(Resource.Success(body))
            }.orElse {
                emit(Resource.Failure(exception = UnsupportedOperationException("No body!")))
            }
        } else {
            emit(Resource.Failure(exception = Exception("Http Code: ${apiCallResponse.code()}, message: ${apiCallResponse.message()}")))
        }
    }.catch { e ->
        emit(Resource.Failure(exception = e))
    }.onStart {
        emit(Resource.Loading)
    }.flowOn(Dispatchers.IO)
}