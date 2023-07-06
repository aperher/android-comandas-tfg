package tfg.aperher.comandas.model

import java.sql.Timestamp

data class Order(val id: String, val tableId: String, val initTime: Timestamp, val articles: List<Article>) {
    data class Article(val id: String, val name: String, val price: Double, val quantity: Int = 1, val state: State = State.PENDING, val extras: List<Ingredient> = emptyList()) {
        enum class State {
            PENDING, PREPARING, READY, DELIVERED
        }
        data class Ingredient(val id: String, val name: String, val price: Double)
    }
}