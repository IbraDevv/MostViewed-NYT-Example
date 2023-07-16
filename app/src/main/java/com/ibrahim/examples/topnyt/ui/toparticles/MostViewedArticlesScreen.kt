package com.ibrahim.examples.topnyt.ui.toparticles

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ibrahim.examples.topnyt.domain.features.articles.Periods
import com.ibrahim.examples.topnyt.ui.theme.GhostWhite
import com.ibrahim.examples.topnyt.ui.theme.TopNYTTheme
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ibrahim.examples.topnyt.R
import com.ibrahim.examples.topnyt.domain.features.articles.models.Article
import com.ibrahim.examples.topnyt.ui.Screens
import com.ibrahim.examples.topnyt.ui.theme.Orange


@Composable
fun MostViewedArticlesScreen(
    navController: NavController, mostViewedViewModel: MostViewedArticlesViewModel = hiltViewModel()
) {
    val periodsUiState by mostViewedViewModel.periodsUiState.collectAsStateWithLifecycle()
    val articlesUiState by mostViewedViewModel.articlesListUiState.collectAsStateWithLifecycle()
    var currentPeriod by rememberSaveable { mutableStateOf(0) }


    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                NyTimesTitle()
            },
            content = { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = GhostWhite),
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            ListOfPeriodsAsRow(
                                periodsUiState.listOfPeriods,
                                currentPeriod,
                                onPeriodChanged = { _index ->
                                    currentPeriod = _index
                                    mostViewedViewModel.getMostViewedArticles(_index)
                                },
                                navController = navController
                            )
                            when (articlesUiState) {
                                is MostViewedArticlesUI.Nothing -> {
                                    // show nothing
                                }

                                is MostViewedArticlesUI.Loading -> {
                                    //Show circular progress indicator
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        CircularProgressIndicator(color = Color.Black)
                                        Spacer(modifier = Modifier.width(width = 8.dp))
                                        Text(
                                            text = stringResource(id = R.string.loading_message),
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }

                                }

                                is MostViewedArticlesUI.MostViewedArticlesList -> {
                                    ListOfArticles(
                                        listOfArticles = (articlesUiState as MostViewedArticlesUI.MostViewedArticlesList).listOfArticles,
                                        onArticleClicked = { _article ->
                                            mostViewedViewModel.setDetailedArticle(_article)
                                            mostViewedViewModel.getDetailedArticle()?.let {
                                                navController.navigate(Screens.ArticleDetailsScreen)
                                            }
                                        }
                                    )
                                }

                                is MostViewedArticlesUI.RatelimitExceeded -> {
                                    ErrorMessageWithRetry(errorMessage = stringResource(id = R.string.ratelimit_exceeded),
                                        onRetryClicked = {
                                            mostViewedViewModel.getMostViewedArticles(currentPeriod)
                                        })
                                }

                                is MostViewedArticlesUI.UnknownError -> {
                                    ErrorMessageWithRetry(errorMessage = stringResource(id = R.string.unknown_error),
                                        onRetryClicked = {
                                            mostViewedViewModel.getMostViewedArticles(currentPeriod)
                                        })
                                }
                            }

                        } //bodyContent()

                    }
                }
            },
        )
    }


}


@Composable
fun ErrorMessageWithRetry(errorMessage: String, onRetryClicked: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            tint = Orange,
            contentDescription = stringResource(id = R.string.error_icon),
            modifier = Modifier.size(AssistChipDefaults.IconSize)
        )
        Spacer(modifier = Modifier.width(width = 8.dp))
        Text(
            text = errorMessage, color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(width = 8.dp))
        Column {
            Button(
                onClick = { onRetryClicked() },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(50), // = 50% percent
                // or shape = CircleShape
                colors = ButtonDefaults.buttonColors(contentColor = Color.White)
            ) {
                Text(
                    text = stringResource(id = R.string.retry),
                    color = MaterialTheme.colorScheme.secondary,
                )

            }
        }

    }


}


@Composable
fun ListOfPeriodsAsRow(
    listOfPeriods: List<Periods>,
    currentPeriod: Int,
    onPeriodChanged: (Int) -> Unit,
    navController: NavController
) {
    LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        items(listOfPeriods) { _period ->
            AssistChip(
                onClick = {
                    onPeriodChanged(_period.ordinal)
                },
                colors = AssistChipDefaults.assistChipColors(containerColor = GhostWhite),
                shape = RoundedCornerShape(2.dp),
                label = {
                    Text(text = _period.name)
                },
                leadingIcon = {
                    AnimatedVisibility(
                        visible = _period.ordinal == currentPeriod
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            tint = Color.Black,
                            contentDescription = "to do : localize",
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }

                },
            )
        }
    }
}


@Composable
fun ListOfArticles(listOfArticles: List<Article>, onArticleClicked: (Article) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ) {
        items(listOfArticles) { _article ->
            ArticleCard(_article, onArticleClicked = onArticleClicked)
        }
    }
}

@Composable
fun ArticleCard(article: Article, onArticleClicked: (Article) -> Unit) {

    val url: String =
        if (article.media.isNotEmpty()) article.media.first().mediaMetadata.first().url
        else ""

    val caption: String =
        if (article.media.isNotEmpty()) article.media.first().mediaMetadata.first().url
        else ""
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                onArticleClicked(article)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            GetArticleImage(url = url, caption = caption)
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = article.title, style = TextStyle(
                        color = Color.Gray, fontSize = 16.sp, fontFamily = FontFamily.Default
                    )
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "${stringResource(id = R.string.published_date).removeSuffix("\n")}: ${article.publishedDate}",
                    style = TextStyle(
                        color = Color.Gray, fontSize = 16.sp, fontFamily = FontFamily.Default
                    ),
                )

            }

        }
    }

}

@Composable
fun GetArticleImage(url: String, caption: String) {
    if (url.isEmpty()) Image(
        painter = painterResource(id = R.drawable.baseline_no_photography_24),
        contentDescription = caption,
        modifier = Modifier
            .height(75.dp)
            .width(75.dp)
    )
    else {
        AsyncImage(
            model = url,
            error = painterResource(id = R.drawable.baseline_error_outline_24),
            placeholder = painterResource(id = R.drawable.baseline_downloading_24),
            contentDescription = caption,
            modifier = Modifier
                .height(75.dp)
                .width(75.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun NyTimesTitle() {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    text = "The New York Times",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Black,
                    style = TextStyle(
                        fontStyle = FontStyle.Italic, fontSize = 22.sp
                    )
                )

            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
    )


}


@Preview
@Composable
fun PreviewArticleCard() {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Row {
            GetArticleImage(url = "", caption = "caption")
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "article.title", style = TextStyle(
                        color = Color.Gray, fontSize = 16.sp, fontFamily = FontFamily.Default
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "article.publishedDate", style = TextStyle(
                        color = Color.Gray, fontSize = 16.sp, fontFamily = FontFamily.Default
                    )
                )
            }
        }
    }

}


@Preview
@Composable
fun PreviewListOfPeriods(
    listOfPeriods: List<Periods> = listOf(
        Periods.Yesterday, Periods.LastMonth
    )
) {
    TopNYTTheme {
//        ListOfPeriodsAsRow(listOfPeriods = listOfPeriods ,)
    }
}

