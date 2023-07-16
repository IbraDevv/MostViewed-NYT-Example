package com.ibrahim.examples.topnyt.data.common

import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber


// In a bigger application this should be in a Custom Call Adapter.
suspend fun <T : Any> handleNetworkRequest(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiSuccess(body)
        } else {
            Timber.d("$response")
            ApiError(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        Timber.d(e)
        ApiError(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        ApiException(e)
    }
}
