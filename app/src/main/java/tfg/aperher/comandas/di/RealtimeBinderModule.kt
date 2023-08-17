package tfg.aperher.comandas.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import tfg.aperher.comandas.data.realtime.RealtimeRepository
import tfg.aperher.comandas.data.realtime.RealtimeRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RealtimeBinderModule {

    @Binds
    @Singleton
    abstract fun bindRealtimeRepository(realtimeRepositoryImpl: RealtimeRepositoryImpl) : RealtimeRepository
}