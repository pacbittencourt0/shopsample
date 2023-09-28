package com.pacbittencourt.shop.ui.orderdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pacbittencourt.shop.domain.usecase.GetOrderDetailsUseCase
import com.pacbittencourt.shop.mapToOrderDetailsUi
import com.pacbittencourt.shop.ui.model.OrderDetailsUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderDetailsViewModel(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _detailsState = MutableStateFlow(OrderDetailsState())
    val detailsState: StateFlow<OrderDetailsState> = _detailsState

    fun loadOrderDetails(orderId: String) {
        viewModelScope.launch(dispatcher) {
            runCatching {
                getOrderDetailsUseCase(orderId.toInt())
            }.onSuccess { flow ->
                flow.collect { order ->
                    _detailsState.update { state ->
                        state.copy(
                            isLoading = false,
                            orderDetailsUi = order.mapToOrderDetailsUi()
                        )
                    }
                }
            }
        }
    }
}

data class OrderDetailsState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val orderDetailsUi: OrderDetailsUi? = null
)

