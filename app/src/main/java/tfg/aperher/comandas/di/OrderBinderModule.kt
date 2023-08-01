package tfg.aperher.comandas.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import tfg.aperher.comandas.data.order.OrderDataSource
import tfg.aperher.comandas.data.order.OrderDataSourceImpl
import tfg.aperher.comandas.data.order.OrderRepository
import tfg.aperher.comandas.data.order.OrderRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class OrderBinderModule {

    @ViewModelScoped
    @Binds
    abstract fun bindOrderRepository(repository: OrderRepositoryImpl): OrderRepository

    @ViewModelScoped
    @Binds
    abstract fun bindOrderDataSource(datasource: OrderDataSourceImpl): OrderDataSource
}