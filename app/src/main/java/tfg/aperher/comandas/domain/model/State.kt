package tfg.aperher.comandas.domain.model

enum class State(val value: String) {
    AVAILABLE(""),
    PENDING("pedido"),
    PREPARING("preparacion"),
    READY("preparado"),
    DELIVERED("servido");

    override fun toString(): String {
        return value
    }
}

