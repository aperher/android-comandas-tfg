package tfg.aperher.comandas.data.utils

import android.util.Log
import retrofit2.Response
import java.io.IOException

inline fun <T, R> Response<T>.toResult(mapper: (T) -> R): Result<R> {
    return if (isSuccessful) {
        Result.success(mapper(body()!!))
    } else {
        Log.d("ResponseEx", "toResult: ${errorBody()?.string()}")
        Result.failure(IOException())
    }
}