package com.ibrahim.examples.topnyt.domain.features.articles.models

import com.google.gson.annotations.SerializedName

data class MediaMetaData(
    val format: String,
    val height: Int,
    val url: String,
    val width: Int
)

