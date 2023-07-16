package com.ibrahim.examples.topnyt.domain.features.articles

import com.ibrahim.examples.topnyt.data.common.Either
import com.ibrahim.examples.topnyt.domain.features.articles.models.Article

interface ArticlesRepository {

    suspend fun getMostPoplarArticles(period: String): Either<GetMostPopularArticlesUseCase.ERROR, List<Article>>

}