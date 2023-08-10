package tfg.aperher.comandas.data.section

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tfg.aperher.comandas.data.section.model.SectionDto
import tfg.aperher.comandas.data.utils.response.safeApiCall
import javax.inject.Inject
import javax.inject.Named

const val ESTABLISHMENT_ID = "ea443834-c2ff-45e9-9504-ab580bcbbe01"

class SectionDataSourceImpl @Inject constructor(@Named("restRetrofit") retrofit: Retrofit) : SectionDataSource {

    private val retrofitSectionService = retrofit.create(SectionRetrofit::class.java)

    override suspend fun getSections(): Response<List<SectionDto>> = safeApiCall {
        retrofitSectionService.getSections(ESTABLISHMENT_ID)
    }

    override suspend fun getSectionById(id: String): Response<SectionDto> = safeApiCall {
        retrofitSectionService.getSectionById(id)
    }

    interface SectionRetrofit {
        @GET("sections")
        suspend fun getSections(@Query("establishment") establishment: String): Response<List<SectionDto>>

        @GET("sections/{id}")
        suspend fun getSectionById(@Path("id") id: String): Response<SectionDto>

    }
}