package com.pacbittencourt.shop.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pacbittencourt.shop.domain.usecase.GetOrdersUseCase
import com.pacbittencourt.shop.formatCurrency
import com.pacbittencourt.shop.mapToOrderSummaryUi
import com.pacbittencourt.shop.ui.model.OrderSummaryUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _orderState = MutableStateFlow(OrdersState())
    val orderState: StateFlow<OrdersState> = _orderState

    fun loadOrders() {
        viewModelScope.launch(dispatcher) {
            _orderState.update {
                it.copy(isLoading = true)
            }
            runCatching {
                getOrdersUseCase()
            }.onSuccess {
                var totalOrders = 0.0
                it.collect {
                    _orderState.update { state ->
                        state.copy(
                            isLoading = false,
                            orders = it.mapIndexed { index, order ->
                                totalOrders += order.orderPrice
                                val id = index.plus(1).toString()
                                order.mapToOrderSummaryUi(id)
                            },
                            totalOrders = totalOrders.formatCurrency()
                        )
                    }
                }
            }
        }
    }
}

data class OrdersState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val orders: List<OrderSummaryUi> = emptyList(),
    val totalOrders: String = ""
)

