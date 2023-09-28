package com.pacbittencourt.shop.ui.viewmodel

import com.pacbittencourt.shop.domain.model.Order
import com.pacbittencourt.shop.domain.usecase.GetOrdersUseCase
import com.pacbittencourt.shop.ui.orders.OrdersViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrdersViewModelTest {

    private val getOrdersUseCase: GetOrdersUseCase = mockk(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `should return all orders successfully`() = runTest {
        every { getOrdersUseCase() } returns flow {
            emit(listOf(Order(items = emptyList(), orderPrice = 0.0)))
        }
        val viewModel =
            OrdersViewModel(getOrdersUseCase, testDispatcher)

        viewModel.loadOrders()
        advanceUntilIdle()

        val resultState = viewModel.orderState.value
        assert(!resultState.isLoading)
        assert(resultState.orders.isNotEmpty())
    }
}