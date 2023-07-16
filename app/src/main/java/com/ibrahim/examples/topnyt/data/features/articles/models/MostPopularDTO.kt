package com.ibrahim.examples.topnyt.data.features.articles.models

import com.google.gson.annotations.SerializedName

data class MostPopularDTO(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("num_results")
    val resultSize: Int,
    @SerializedName("results")
    val articles: List<ArticleDTO>,
    @SerializedName("status")
    val status: String
)