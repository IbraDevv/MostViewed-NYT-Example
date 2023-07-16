package com.ibrahim.examples.topnyt.data.features.articles.models

import com.google.gson.annotations.SerializedName
import com.ibrahim.examples.topnyt.domain.features.articles.models.Media

data class MediaDTO(
    @SerializedName("approved_for_syndication")
    val approvedForDyndication: Int,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("media-metadata")
    val mediaMetadata: List<MediaMetadataDTO>,
    @SerializedName("subtype")
    val subtype: String,
    @SerializedName("type")
    val type: String
)


fun List<MediaDTO>.toDomain(): List<Media> {
    val list = mutableListOf<Media>()
    this.forEach {
        list.add(it.toDomain())
    }
    return list
}

fun MediaDTO.toDomain(): Media {
    return Media(caption = caption, copyright = copyright, mediaMetadata = mediaMetadata.toDomain())
}