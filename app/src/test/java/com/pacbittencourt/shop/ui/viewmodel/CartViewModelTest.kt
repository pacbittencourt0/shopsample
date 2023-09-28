package com.pacbittencourt.shop.ui.viewmodel

import com.pacbittencourt.shop.domain.model.Item
import com.pacbittencourt.shop.domain.model.ItemOrder
import com.pacbittencourt.shop.domain.usecase.ClearCartUseCase
import com.pacbittencourt.shop.domain.usecase.GetCurrentCartUseCase
import com.pacbittencourt.shop.domain.usecase.PlaceOrderUseCase
import com.pacbittencourt.shop.ui.cart.CartViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private val placeOrderUseCase: PlaceOrderUseCase = mockk()
    private val getCartUseCase: GetCurrentCartUseCase = mockk()
    private val clearCartUseCase: ClearCartUseCase = mockk(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `when cart is empty should return empty state`() = runTest {
        every { getCartUseCase() } returns flow { emit(emptyList()) }
        val viewModel =
            CartViewModel(placeOrderUseCase, getCartUseCase, clearCartUseCase, testDispatcher)

        viewModel.loadCurrentCart()
        advanceUntilIdle()

        val resultState = viewModel.cartState.value
        assert(!resultState.isLoading)
        assert(resultState.cart.isEmpty())
    }

    @Test
    fun `when cart is not empty should return state filled`() = runTest {
        val cart: List<ItemOrder> = listOf(
            ItemOrder(Item(1, "", 1.0), quantity = 1.0, totalPrice = 1.0)
        )
        every { getCartUseCase() } returns flow { emit(cart) }
        val viewModel =
            CartViewModel(placeOrderUseCase, getCartUseCase, clearCartUseCase, testDispatcher)

        viewModel.loadCurrentCart()
        advanceUntilIdle()

        val resultState = viewModel.cartState.value
        assert(!resultState.isLoading)
        assert(resultState.cart.isNotEmpty())
        assert(resultState.cartTotalPrice == "1,00")
    }

    @Test
    fun `when clear cart should call clear cart`() = runTest {
        val viewModel =
            CartViewModel(placeOrderUseCase, getCartUseCase, clearCartUseCase, testDispatcher)

        viewModel.clearOrder()
        advanceUntilIdle()

        verify { clearCartUseCase() }
    }

    @Test
    fun `when assemble cart state should be success`() = runTest {
        val cart: List<ItemOrder> = listOf(
            ItemOrder(Item(1, "", 1.0), quantity = 1.0, totalPrice = 1.0)
        )
        every { getCartUseCase() } returns flow { emit(cart) }
        every { placeOrderUseCase(any()) } returns true
        val viewModel =
            CartViewModel(placeOrderUseCase, getCartUseCase, clearCartUseCase, testDispatcher)

        viewModel.loadCurrentCart()
        advanceUntilIdle()
        viewModel.assembleOrder()
        advanceUntilIdle()

        val resultState = viewModel.cartState.value
        assert(!resultState.isLoading)
        assert(resultState.cart.isEmpty())
        assert(resultState.orderRequestSuccess)
        assert(resultState.cartTotalPrice == "")
    }

}