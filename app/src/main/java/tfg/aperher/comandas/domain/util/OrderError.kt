package tfg.aperher.comandas.domain.util

sealed class OrderError : Exception() {
    object EmptyOrderError : OrderError()
    object OrderNotSavedError : OrderError()
    object SameOrderError : OrderError()
    object SendOrderError : OrderError()
}