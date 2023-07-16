package com.ibrahim.examples.topnyt.domain.features.articles

import com.ibrahim.examples.topnyt.data.common.Either
import com.ibrahim.examples.topnyt.domain.features.articles.models.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetMostPopularArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository
) : suspend (String) -> Either<GetMostPopularArticlesUseCase.ERROR, Flow<List<Article>>> {


    override suspend operator fun invoke(period: String): Either<ERROR, Flow<List<Article>>> {
        return when (val dataResponse = articlesRepository.getMostPoplarArticles(period = period)) {
            is Either.Failure -> {
                Either.Failure(dataResponse.value)
            }

            is Either.Success -> {
                Either.Success(
                    flow {
                        emit(dataResponse.value)
                    }
                )
            }
        }
    }


    sealed class ERROR {
        data class UnknownError(val errorMessage: String) : ERROR()
        object RatelimitExceeded : ERROR()
    }
}