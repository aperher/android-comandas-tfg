package tfg.aperher.comandas.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import tfg.aperher.comandas.data.article.ArticleDataSource
import tfg.aperher.comandas.data.article.ArticleDataSourceImpl
import tfg.aperher.comandas.data.article.ArticleRepository
import tfg.aperher.comandas.data.article.ArticleRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class ArticleBinderModule {
    @Binds
    @ViewModelScoped
    abstract fun bindArticleRepository(repository: ArticleRepositoryImpl): ArticleRepository

    @Binds
    @ViewModelScoped
    abstract fun bindArticleDataSource(repository: ArticleDataSourceImpl): ArticleDataSource
}