package tfg.aperher.comandas.data.realtime

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class RealtimeDataSourceImpl @Inject constructor(@Named("sseRetrofit") retrofit: Retrofit): RealtimeDataSource {
    private val retrofitRealtimeService = retrofit.create(RealtimeRetrofit::class.java)

    interface RealtimeRetrofit {

    }
}