package com.ibrahim.examples.topnyt.data.features.articles.models

import com.google.gson.annotations.SerializedName
import com.ibrahim.examples.topnyt.domain.features.articles.models.Article

data class ArticleDTO(
    @SerializedName("abstract")
    val abstract: String,
    @SerializedName("adx_keywords")
    val adxKeywords: String,
    @SerializedName("asset_id")
    val assetId: Long,
    @SerializedName("byline")
    val author: String,
    @SerializedName("keywords")
    val column: Any?,
    @SerializedName("des_facet")
    val desFacet: List<String>,
    @SerializedName("eta_id")
    val etaId: Int,
    @SerializedName("geo_facet")
    val geoFacet: List<String>,
    @SerializedName("id")
    val id: Long,
    @SerializedName("media")
    val mediaList: List<MediaDTO>,
    @SerializedName("nytdsection")
    val nytdsection: String,
    @SerializedName("org_facet")
    val orgFacet: List<String>,
    @SerializedName("per_facet")
    val perFacet: List<String>,
    @SerializedName("published_date")
    val publishedDate: String,
    @SerializedName("section")
    val section: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("subsection")
    val subsection: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated")
    val updated: String,
    @SerializedName("uri")
    val uri: String,
    @SerializedName("url")
    val url: String
)

fun ArticleDTO.toDomainArticle(): Article {
    val media = mediaList
    return Article(title = title, type = type, author = author, abstract = abstract, keywords = desFacet, section = section , publishedDate = publishedDate , media = mediaList.toDomain())
}