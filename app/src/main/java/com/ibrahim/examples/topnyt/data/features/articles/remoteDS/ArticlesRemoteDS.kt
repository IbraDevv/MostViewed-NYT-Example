package com.ibrahim.examples.topnyt.data.features.articles.remoteDS

import com.ibrahim.examples.topnyt.common.IoDispatcher
import com.ibrahim.examples.topnyt.data.common.NetworkResult
import com.ibrahim.examples.topnyt.data.common.handleNetworkRequest
import com.ibrahim.examples.topnyt.data.features.articles.models.MostPopularDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticlesRemoteDS @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher ,
    private val serviceApi: ArticlesServiceApi
) {

    suspend fun getMostPoplarArticles(period: String): NetworkResult<MostPopularDTO> =
        withContext(ioDispatcher) {
            handleNetworkRequest { serviceApi.getMostPopularArticles(period) }
        }


}