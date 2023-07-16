package com.ibrahim.examples.topnyt.data.features.articles.di

import com.ibrahim.examples.topnyt.common.IoDispatcher
import com.ibrahim.examples.topnyt.data.common.di.NetworkModule
import com.ibrahim.examples.topnyt.data.features.articles.ArticlesRepositoryImpl
import com.ibrahim.examples.topnyt.data.features.articles.remoteDS.ArticlesRemoteDS
import com.ibrahim.examples.topnyt.data.features.articles.remoteDS.ArticlesServiceApi
import com.ibrahim.examples.topnyt.domain.features.articles.ArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object ArticlesModule {


    @Provides
    fun provideArticlesServiceApi(retrofit: Retrofit): ArticlesServiceApi =
        retrofit.create(ArticlesServiceApi::class.java)


    @Provides
    fun providesArticlesRemoteDataSource(
        articlesServiceApi: ArticlesServiceApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ArticlesRemoteDS {
        return ArticlesRemoteDS(serviceApi = articlesServiceApi, ioDispatcher = ioDispatcher)
    }

    @Provides
    fun providesArticlesRepository(articlesRemoteDS: ArticlesRemoteDS): ArticlesRepository {
        return ArticlesRepositoryImpl(remoteDS = articlesRemoteDS)
    }

}