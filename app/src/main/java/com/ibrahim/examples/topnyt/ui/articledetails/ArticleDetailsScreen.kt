package com.ibrahim.examples.topnyt.ui.articledetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ibrahim.examples.topnyt.ui.toparticles.MostViewedArticlesViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ibrahim.examples.topnyt.R
import com.ibrahim.examples.topnyt.domain.features.articles.models.Article
import com.ibrahim.examples.topnyt.ui.toparticles.GetArticleImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsScreen(
    navController: NavController, mostViewedViewModel: MostViewedArticlesViewModel = hiltViewModel()
) {
    val article by remember {
        mutableStateOf(mostViewedViewModel.getDetailedArticle())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.article_details),
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Black,
                        style = TextStyle(
                            fontStyle = FontStyle.Italic, fontSize = 22.sp
                        )
                    )
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {

                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }

                    } else {
                        null
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.primary
            ) {
                article?.let { _article ->
                    ArticleDetails(_article)
                }
            }
        }

    )

}

@Composable
fun ArticleDetails(article: Article) {
    val url: String =
        if (article.media.isNotEmpty()) article.media.first().mediaMetadata.last().url
        else ""
    val caption: String =
        if (article.media.isNotEmpty()) article.media.first().caption
        else ""

    Column(modifier = Modifier.fillMaxSize()) {
        GetArticleDetailedImage(url = url, caption = caption)
        Text(
            textAlign = TextAlign.Center,
            text = article.title,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Black,
            color = Color.Black,
            style = TextStyle(
                fontStyle = FontStyle.Italic, fontSize = 22.sp
            )
        )
        Text(
            textAlign = TextAlign.Center,
            text = "Section: ${article.section}",
            fontFamily = FontFamily.Serif,
            color = Color.Black,
            style = TextStyle(
                fontStyle = FontStyle.Italic, fontSize = 14.sp
            )
        )
        Text(
            text = "${
                stringResource(id = R.string.published_date).removeSuffix(
                    "\n"
                )
            }: ${article.publishedDate}",
            style = TextStyle(
                color = Color.Gray,
                fontSize = 12.sp,
                fontFamily = FontFamily.Default
            ),
        )
        Text(
            textAlign = TextAlign.Center,
            text = article.abstract,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Black,
            color = Color.Black,
            style = TextStyle(
                fontStyle = FontStyle.Italic, fontSize = 22.sp
            ),
            modifier = Modifier.padding(4.dp)
        )
        var keywords = "Keywords: "
        article.keywords.forEach { keyword ->
            keywords += "$keyword, "
        }
        keywords += ". "
        Text(
            text = keywords,
            style = TextStyle(
                color = Color.Gray,
                fontSize = 12.sp,
                fontFamily = FontFamily.Default
            ),
        )


    }

}

@Composable
fun GetArticleDetailedImage(url: String, caption: String) {
    if (url.isEmpty()) Image(
        painter = painterResource(id = R.drawable.baseline_no_photography_24),
        contentDescription = caption,
        modifier = Modifier
            .height(293.dp)
            .width(440.dp)
    )
    else {
        AsyncImage(
            model = url,
            error = painterResource(id = R.drawable.baseline_error_outline_24),
            placeholder = painterResource(id = R.drawable.baseline_downloading_24),
            contentDescription = caption,
            modifier = Modifier
                .height(293.dp)
                .width(440.dp)
        )
    }
}
