package com.ibrahim.examples.topnyt


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ibrahim.examples.topnyt.ui.Screens
import com.ibrahim.examples.topnyt.ui.articledetails.ArticleDetailsScreen
import com.ibrahim.examples.topnyt.ui.theme.TopNYTTheme
import com.ibrahim.examples.topnyt.ui.toparticles.MostViewedArticlesScreen
import com.ibrahim.examples.topnyt.ui.toparticles.MostViewedArticlesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopNYTTheme {
                val navController = rememberNavController()
                val mostViewedViewModel: MostViewedArticlesViewModel = hiltViewModel()
                NavHost(navController = navController, startDestination = Screens.MostViewedArticlesScreen) {
                    composable(Screens.MostViewedArticlesScreen) {
                        MostViewedArticlesScreen(navController = navController , mostViewedViewModel)
                    }
                    composable(Screens.ArticleDetailsScreen) {
                        ArticleDetailsScreen(navController = navController, mostViewedViewModel)
                    }
                }
            }
        }
    }


}
