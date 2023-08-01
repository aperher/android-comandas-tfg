package tfg.aperher.comandas.data.utils

import retrofit2.Response
import java.io.IOException

inline fun <T, R> Response<T>.toResult(mapper: (T) -> R): Result<R> {
    return if (isSuccessful) {
        Result.success(mapper(body()!!))
    } else {
        Result.failure(IOException())
    }
}