package com.ibrahim.examples.topnyt.data.features.articles

import com.ibrahim.examples.topnyt.data.common.ApiError
import com.ibrahim.examples.topnyt.data.common.ApiException
import com.ibrahim.examples.topnyt.data.common.ApiSuccess
import com.ibrahim.examples.topnyt.data.common.Either
import com.ibrahim.examples.topnyt.data.features.articles.models.toDomainArticle
import com.ibrahim.examples.topnyt.data.features.articles.remoteDS.ArticlesRemoteDS
import com.ibrahim.examples.topnyt.domain.features.articles.ArticlesRepository
import com.ibrahim.examples.topnyt.domain.features.articles.GetMostPopularArticlesUseCase
import com.ibrahim.examples.topnyt.domain.features.articles.models.Article
import timber.log.Timber
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val remoteDS: ArticlesRemoteDS
) : ArticlesRepository {


    override suspend fun getMostPoplarArticles(period: String): Either<GetMostPopularArticlesUseCase.ERROR, List<Article>> {
        val response = remoteDS.getMostPoplarArticles(period)
        Timber.d("Response is : $response")
        return when (response) {
            is ApiSuccess -> {
                val domainArticles = mutableListOf<Article>()
                response.data.articles.forEach { _articleDTO ->
                    domainArticles.add(_articleDTO.toDomainArticle())
                }
                Either.Success(domainArticles.toList())
            }

            is ApiError -> {
                when (response.code) {
                    429 -> Either.Failure(GetMostPopularArticlesUseCase.ERROR.RatelimitExceeded)
                    else -> {
                        Timber.d("Unknown Error 1 : ${response.code}")
                        val errorMessage = response.message ?: "Unknown Error"
                        Either.Failure(GetMostPopularArticlesUseCase.ERROR.UnknownError(errorMessage = errorMessage))
                    }
                }
            }

            is ApiException -> {
                Timber.d("Unknown Error 2 : ${response.e}")
                val errorMessage = response.e.message ?: "Unknown Error"
                Either.Failure(GetMostPopularArticlesUseCase.ERROR.UnknownError(errorMessage = errorMessage))
            }
        }
    }
}