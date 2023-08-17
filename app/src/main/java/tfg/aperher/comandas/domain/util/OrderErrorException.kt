package tfg.aperher.comandas.domain.util

sealed class OrderErrorException : Exception() {
    object EmptyOrderError : OrderErrorException()
    object OrderNotSavedError : OrderErrorException()
    object SameOrderError : OrderErrorException()
    object SendOrderError : OrderErrorException()
}
