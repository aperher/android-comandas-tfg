package tfg.aperher.comandas.presentation.orders.recordsorders

import tfg.aperher.comandas.domain.model.User

data class RecordOrdersViewState(
    val waiter: User? = null,
    val dateMillis: Long? = null,
    val isProgressLoading: Boolean = false,
    val isPullToRefresh: Boolean = false,
    val isShimmerLoading: Boolean = true
) {
    fun isInitLoading() = isShimmerLoading
}