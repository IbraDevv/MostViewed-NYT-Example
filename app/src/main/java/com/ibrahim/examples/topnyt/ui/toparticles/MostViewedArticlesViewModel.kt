package com.ibrahim.examples.topnyt.ui.toparticles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrahim.examples.topnyt.data.common.Either
import com.ibrahim.examples.topnyt.domain.features.articles.GetMostPopularArticlesUseCase
import com.ibrahim.examples.topnyt.domain.features.articles.GetPeriodsUseCase
import com.ibrahim.examples.topnyt.domain.features.articles.Periods
import com.ibrahim.examples.topnyt.domain.features.articles.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MostViewedArticlesViewModel @Inject constructor(
    private val getMostPopularArticles: GetMostPopularArticlesUseCase,
    private val getPeriods: GetPeriodsUseCase
) : ViewModel() {

    // Periods UI state
    private val _periodsUiState by lazy {
        MutableStateFlow<PeriodsListUiState>(
            PeriodsListUiState(
                listOfPeriods = emptyList(), 0
            )
        )
    }

    val periodsUiState: StateFlow<PeriodsListUiState> = _periodsUiState.asStateFlow()


    private val _articlesListUiState =
        MutableStateFlow<MostViewedArticlesUI>(MostViewedArticlesUI.Nothing)
    val articlesListUiState: StateFlow<MostViewedArticlesUI> = _articlesListUiState.asStateFlow()


    private var article: Article? = null

    init {
        viewModelScope.launch {
            val listOfPeriods = getPeriods()
            if (listOfPeriods.isNotEmpty()) {
                _periodsUiState.emit(PeriodsListUiState(listOfPeriods = listOfPeriods, 0))
                // request articles with the first period in the list as default
                getMostViewedArticles(0)
            }
        }
    }


    fun setDetailedArticle(article: Article) {
        this.article = article
    }

    fun getDetailedArticle(): Article? {
        return article
    }

    fun getMostViewedArticles(index: Int) {
        viewModelScope.launch {
            _articlesListUiState.emit(MostViewedArticlesUI.Loading)
            when (val articlesStatus = getMostPopularArticles(Periods.values()[index].value)) {
                is Either.Success -> {
                    articlesStatus.value.collect { _articles ->
                        _articles.forEach { _article ->
                            Timber.d("Article: $_article")
                        }
                        Timber.d("ArticlesUiState : ${_articlesListUiState.value} This the Articles ${_articles}")
                        _articlesListUiState.emit(
                            MostViewedArticlesUI.MostViewedArticlesList(_articles)
                        )

                    }
                }

                is Either.Failure -> {
                    _articlesListUiState.emit(articlesStatus.value.toPresentationError())
                }
            }
        }
    }


}

