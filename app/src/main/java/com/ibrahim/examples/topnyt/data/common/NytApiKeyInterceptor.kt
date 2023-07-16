package com.ibrahim.examples.topnyt.data.common

import com.ibrahim.examples.topnyt.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class NytApiKeyInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val newUrl =
            originalUrl.newBuilder().addQueryParameter("api-key", BuildConfig.NYT_API_KEY).build()
        val newRequest = originalRequest.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }
}