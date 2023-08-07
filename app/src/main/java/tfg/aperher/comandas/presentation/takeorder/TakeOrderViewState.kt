package tfg.aperher.comandas.presentation.takeorder

import tfg.aperher.comandas.utils.Event

data class TakeOrderViewState(
    val sectionName: String = "",
    val tableNumber: Int = 1,
    val showBottomSheet: Event<Boolean> = Event(false),
    val progressBarLoading: Boolean = false
)