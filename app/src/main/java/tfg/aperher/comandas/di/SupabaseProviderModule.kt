package tfg.aperher.comandas.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.serializer.MoshiSerializer
import tfg.aperher.comandas.BuildConfig
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
class SupabaseProviderModule {
    @Provides
    @Singleton
    fun provideSupabaseClient() = createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            install(Realtime) {
                reconnectDelay = 5.seconds
            }
            defaultSerializer = MoshiSerializer()
        }
    }
