package tfg.aperher.comandas.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import tfg.aperher.comandas.data.user.UserDataSource
import tfg.aperher.comandas.data.user.UserDataSourceImpl
import tfg.aperher.comandas.data.user.UserRepository
import tfg.aperher.comandas.data.user.UserRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserBinderModule {
    @Binds
    @ViewModelScoped
    abstract fun bindUserRepository(UserRepositoryImpl: UserRepositoryImpl) : UserRepository

    @Binds
    @ViewModelScoped
    abstract fun bindUserDataSource(UserDataSourceImpl: UserDataSourceImpl) : UserDataSource
}