package tfg.aperher.comandas.domain.model

enum class State(val value: String) {
    AVAILABLE(""),
    PENDING("pedido"),
    PREPARING("preparacion"),
    READY("preparado"),
    DELIVERED("servido");

    companion object {
        fun fromValue(value: String) = values().find { it.value == value }
    }

    override fun toString(): String {
        return value
    }
}

