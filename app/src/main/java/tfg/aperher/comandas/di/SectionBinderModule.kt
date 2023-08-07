package tfg.aperher.comandas.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import tfg.aperher.comandas.data.section.SectionDataSource
import tfg.aperher.comandas.data.section.SectionDataSourceImpl
import tfg.aperher.comandas.data.section.SectionRepository
import tfg.aperher.comandas.data.section.SectionRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class SectionBinderModule {

    @ViewModelScoped
    @Binds
    abstract fun bindSectionRepository(repository: SectionRepositoryImpl): SectionRepository

    @ViewModelScoped
    @Binds
    abstract fun bindSectionDataSource(datasource: SectionDataSourceImpl): SectionDataSource
}