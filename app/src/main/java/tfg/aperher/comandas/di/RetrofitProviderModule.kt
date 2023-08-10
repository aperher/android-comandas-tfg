package tfg.aperher.comandas.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitProviderModule {
    @Provides
    @Named("restRetrofit")
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.141:8080/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Named("sseRetrofit")
    @Singleton
    fun provideRetrofitSSE(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.141:5000/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}