package tfg.aperher.comandas.data.utils

import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

inline fun <T> safeApiCall(apiCall: () -> Response<T>): Response<T> {
    return try {
        apiCall.invoke()
    } catch (e: Exception) {
        Response.error(400, ResponseBody.create(MediaType.parse("text/plain"), e.toString()))
    }
}