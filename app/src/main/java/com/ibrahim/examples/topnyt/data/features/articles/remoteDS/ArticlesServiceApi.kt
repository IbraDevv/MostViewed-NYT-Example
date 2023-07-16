package com.ibrahim.examples.topnyt.data.features.articles.remoteDS

import com.ibrahim.examples.topnyt.data.features.articles.models.MostPopularDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ArticlesServiceApi {


    @GET("/svc/mostpopular/v2/viewed/{period}.json")
    suspend fun getMostPopularArticles(@Path("period") period: String): Response<MostPopularDTO>


}