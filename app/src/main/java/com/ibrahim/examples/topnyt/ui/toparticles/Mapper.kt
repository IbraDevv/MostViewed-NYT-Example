package com.ibrahim.examples.topnyt.ui.toparticles

import com.ibrahim.examples.topnyt.domain.features.articles.GetMostPopularArticlesUseCase


fun GetMostPopularArticlesUseCase.ERROR.toPresentationError(): MostViewedArticlesUI {
    return when (this) {
        is GetMostPopularArticlesUseCase.ERROR.UnknownError -> MostViewedArticlesUI.UnknownError(
            errorMessage = errorMessage
        )

        is GetMostPopularArticlesUseCase.ERROR.RatelimitExceeded -> MostViewedArticlesUI.RatelimitExceeded
    }
}