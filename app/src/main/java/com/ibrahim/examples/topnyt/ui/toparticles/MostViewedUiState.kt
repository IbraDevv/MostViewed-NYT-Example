package com.ibrahim.examples.topnyt.ui.toparticles

import com.ibrahim.examples.topnyt.domain.features.articles.Periods
import com.ibrahim.examples.topnyt.domain.features.articles.models.Article


data class PeriodsListUiState(var listOfPeriods: List<Periods> , var currentIndex : Int)

sealed class MostViewedArticlesUI {
    object Nothing : MostViewedArticlesUI()
    data class UnknownError(val errorMessage: String) : MostViewedArticlesUI()
    object RatelimitExceeded : MostViewedArticlesUI()
    object Loading : MostViewedArticlesUI()
    data class MostViewedArticlesList(var listOfArticles: List<Article>) : MostViewedArticlesUI()
}



