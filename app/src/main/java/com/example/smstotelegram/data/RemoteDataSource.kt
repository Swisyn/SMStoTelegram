package com.example.smstotelegram.data

import com.example.smstotelegram.data.vo.Resource
import com.example.smstotelegram.utils.extensions.orElse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response


/**
 * Created by Cuneyt AYYILDIZ on 4/12/2021.
 */
abstract class RemoteDataSource {
    suspend fun <T : Any> sendTelegramMessage(
        botToken: String,
        chatId: String,
        call: suspend () -> Response<T>
    ): Flow<Resource<T>> {
        return flow {
            if (botToken.isNotEmpty() && chatId.isNotEmpty()) {
                val apiCallResponse = call.invoke()

                if (apiCallResponse.isSuccessful) {
                    apiCallResponse.body()?.let { body ->
                        emit(Resource.Success(body))
                    }.orElse {
                        emit(
                            Resource.Failure(
                                exception = UnsupportedOperationException("No body!")
                            )
                        )
                    }
                } else {
                    emit(
                        Resource.Failure(
                            exception = Exception("Http Code: ${apiCallResponse.code()}, message: ${apiCallResponse.message()}")
                        )
                    )
                }
            } else {
                emit(
                    Resource.Failure(
                        exception = Exception("botToken or chatId was empty!")
                    )
                )
            }
        }.catch { e ->
            emit(
                Resource.Failure(
                    exception = e
                )
            )
        }.onStart {
            emit(Resource.Loading)
        }.flowOn(Dispatchers.IO)
    }
}