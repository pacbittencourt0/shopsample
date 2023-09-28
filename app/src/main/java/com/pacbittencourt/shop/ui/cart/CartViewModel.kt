package com.pacbittencourt.shop.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pacbittencourt.shop.domain.model.Order
import com.pacbittencourt.shop.domain.usecase.ApplyDiscountUseCase
import com.pacbittencourt.shop.domain.usecase.ClearCartUseCase
import com.pacbittencourt.shop.domain.usecase.DeleteItemUseCase
import com.pacbittencourt.shop.domain.usecase.GetCurrentCartUseCase
import com.pacbittencourt.shop.domain.usecase.PlaceOrderUseCase
import com.pacbittencourt.shop.formatCurrency
import com.pacbittencourt.shop.mapToItemOrderUi
import com.pacbittencourt.shop.ui.model.ItemOrderUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val placeOrderUseCase: PlaceOrderUseCase,
    private val getCurrentCartUseCase: GetCurrentCartUseCase,
    private val clearCart: ClearCartUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val applyDiscountUseCase: ApplyDiscountUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private lateinit var order: Order

    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> = _cartState

    fun loadCurrentCart() {
        viewModelScope.launch(dispatcher) {
            _cartState.update {
                it.copy(isLoading = true, orderRequestSuccess = false)
            }
            runCatching {
                getCurrentCartUseCase()
            }.onSuccess { flow ->
                flow.collect {
                    var totalPrice = 0.0
                    val mappedList = it.map { itemOrder ->
                        val itemOrderPrice = itemOrder.quantity * itemOrder.item.price
                        totalPrice += itemOrderPrice
                        itemOrder.mapToItemOrderUi()
                    }
                    _cartState.update { state ->
                        state.copy(
                            isLoading = false,
                            cart = mappedList,
                            cartTotalPrice = totalPrice.formatCurrency()
                        )
                    }
                    order = Order(it, totalPrice)
                }
            }
        }
    }

    fun assembleOrder() {
        if (placeOrderUseCase(order)) {
            _cartState.update {
                it.copy(
                    isLoading = false,
                    cart = emptyList(),
                    cartTotalPrice = "",
                    orderRequestSuccess = true
                )
            }
        }
    }

    fun clearOrder() {
        _cartState.update {
            CartState()
        }
        clearCart()
        loadCurrentCart()
    }

    fun deleteItem(index: Int) {
        deleteItemUseCase(index)
        loadCurrentCart()
    }

    fun applyDiscount(discount: Double) {
        applyDiscountUseCase(discount)
        loadCurrentCart()
    }

}

data class CartState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val cart: List<ItemOrderUi> = emptyList(),
    val cartTotalPrice: String = "",
    val orderRequestSuccess: Boolean = false,
)
