package com.ibrahim.examples.topnyt.data.features.articles.models

import com.google.gson.annotations.SerializedName
import com.ibrahim.examples.topnyt.domain.features.articles.models.Media
import com.ibrahim.examples.topnyt.domain.features.articles.models.MediaMetaData

data class MediaMetadataDTO(
    @SerializedName("format")
    val format: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)


fun List<MediaMetadataDTO>.toDomain(): List<MediaMetaData> {
    val list = mutableListOf<MediaMetaData>()
    this.forEach {
        list.add(it.toDomain())
    }
    return list
}

fun MediaMetadataDTO.toDomain(): MediaMetaData {
    return MediaMetaData(format = format, height = height, url = url, width = width)
}