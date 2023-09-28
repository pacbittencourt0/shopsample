package com.pacbittencourt.shop.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pacbittencourt.shop.domain.usecase.AddItemUseCase
import com.pacbittencourt.shop.domain.usecase.GetAvailableItems
import com.pacbittencourt.shop.formatCurrency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getAvailableItems: GetAvailableItems,
    private val addItemUseCase: AddItemUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _itemsState = MutableStateFlow(ItemsState())
    val itemsState: StateFlow<ItemsState> = _itemsState

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                getAvailableItems()
            }.onSuccess { flow ->
                flow.collect {
                    _itemsState.update { state ->
                        state.copy(
                            isLoading = false,
                            items = it.map {
                                ItemUi(it.id, it.description, it.price.formatCurrency())
                            }
                        )
                    }
                }
            }
        }
    }

    fun addItemToCart(id: Int, quantity: Double) {
        addItemUseCase(id, quantity)
    }
}

data class ItemsState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val items: List<ItemUi> = emptyList(),
)

data class ItemUi(
    val id: Int,
    val description: String,
    val price: String
)
