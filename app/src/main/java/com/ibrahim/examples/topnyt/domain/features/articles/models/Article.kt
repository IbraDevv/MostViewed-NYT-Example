package com.ibrahim.examples.topnyt.domain.features.articles.models

data class Article(
    val title: String,
    val type: String,
    val author: String,
    val section: String,
    val abstract: String,
    val keywords: List<String>,
    val publishedDate: String,
    val media: List<Media>
)


