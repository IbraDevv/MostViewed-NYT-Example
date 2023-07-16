package com.ibrahim.examples.topnyt.domain.features.articles.models

import com.google.gson.annotations.SerializedName

data class Media(
    val caption: String,
    val copyright: String,
    val mediaMetadata: List<MediaMetaData>,
)

