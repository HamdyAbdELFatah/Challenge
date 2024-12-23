package com.vodafone.core.base;

import com.vodafone.core.utils.DataResult
import kotlinx.coroutines.flow.Flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import kotlinx.coroutines.flow.flow

abstract class BaseRepository {
    protected suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<DataResult<T>> = flow {
        emit(DataResult.Loading())
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(DataResult.Success(it))
                } ?: emit(DataResult.Error("Response body is null"))
            } else {
                emit(DataResult.Error("Error: ${response.code()} ${response.message()}"))
            }
        } catch (exception: Exception) {
            emit(DataResult.Error(handleApiError(exception)))
        }
    }

    private fun handleApiError(exception: Exception): String = when (exception) {
        is HttpException -> "HTTP Error: ${exception.code()} - ${exception.message()}"
        is IOException -> "Network Error: ${exception.message ?: "Unknown error"}"
        else -> "Unexpected Error: ${exception.localizedMessage ?: "Unknown error"}"
    }
}
