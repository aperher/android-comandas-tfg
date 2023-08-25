package tfg.aperher.comandas.data.user

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import tfg.aperher.comandas.data.section.ESTABLISHMENT_ID
import tfg.aperher.comandas.data.user.model.UserDto
import tfg.aperher.comandas.data.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Named

class UserDataSourceImpl @Inject constructor(@Named("restRetrofit") retrofit: Retrofit) :
    UserDataSource {
    private val userRetrofit = retrofit.create(UserRetrofit::class.java)

    override suspend fun login(email: String, password: String): Response<UserDto> = safeApiCall {
        val userDto = UserDto(email = email, password = password)
        userRetrofit.login(userDto)
    }

    override suspend fun getEstablishmentWaiters(): Response<List<UserDto>> = safeApiCall {
        userRetrofit.getEstablishmentWaiters(ESTABLISHMENT_ID)
    }

    interface UserRetrofit {
        @POST("users/login")
        suspend fun login(@Body loginDto: UserDto): Response<UserDto>

        @GET("users/waiters")
        suspend fun getEstablishmentWaiters(@Query("establishmentId") establishmentId: String): Response<List<UserDto>>
    }
}