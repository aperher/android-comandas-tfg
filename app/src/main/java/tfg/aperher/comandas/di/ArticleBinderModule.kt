package tfg.aperher.comandas.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import tfg.aperher.comandas.data.article.ArticleDataSource
import tfg.aperher.comandas.data.article.ArticleDataSourceImpl
import tfg.aperher.comandas.data.article.ArticleRepository
import tfg.aperher.comandas.data.article.ArticleRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleBinderModule {
    @Binds
    @Singleton
    abstract fun bindArticleRepository(repository: ArticleRepositoryImpl): ArticleRepository

    @Binds
    @Singleton
    abstract fun bindArticleDataSource(repository: ArticleDataSourceImpl): ArticleDataSource
}