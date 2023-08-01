package tfg.aperher.comandas.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import tfg.aperher.comandas.data.category.CategoryDataSource
import tfg.aperher.comandas.data.category.CategoryDataSourceImpl
import tfg.aperher.comandas.data.category.CategoryRepository
import tfg.aperher.comandas.data.category.CategoryRepositoryImpl


@Module
@InstallIn(ViewModelComponent::class)
abstract class CategoryBinderModule {

    @ViewModelScoped
    @Binds
    abstract fun bindSectionRepository(repository: CategoryRepositoryImpl): CategoryRepository

    @ViewModelScoped
    @Binds
    abstract fun bindSectionDataSource(repository: CategoryDataSourceImpl): CategoryDataSource
}