package tfg.aperher.comandas.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.plugins.standaloneSupabaseModule
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.serializer.MoshiSerializer
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
class SupabaseProviderModule {
    @Provides
    @Singleton
    fun provideSupabaseClient() = createSupabaseClient(
        supabaseUrl = "https://armmwvfspccdhqehvjrp.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFybW13dmZzcGNjZGhxZWh2anJwIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY3NTg1MjM4MCwiZXhwIjoxOTkxNDI4MzgwfQ.I0sBSODYnpAwFqHtxSFdvaDpM4tQgfu4sQWkXRPqoO8"
    ) {
        install(Realtime) {
            reconnectDelay = 5.seconds // Default: 7 seconds
        }
        defaultSerializer = MoshiSerializer()
    }
    /*fun provideRealtime() = standaloneSupabaseModule(
        Realtime,
        url = "https://armmwvfspccdhqehvjrp.supabase.co",
        apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFybW13dmZzcGNjZGhxZWh2anJwIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY3NTg1MjM4MCwiZXhwIjoxOTkxNDI4MzgwfQ.I0sBSODYnpAwFqHtxSFdvaDpM4tQgfu4sQWkXRPqoO8"
    ) {
        serializer = MoshiSerializer()
    }*/
}