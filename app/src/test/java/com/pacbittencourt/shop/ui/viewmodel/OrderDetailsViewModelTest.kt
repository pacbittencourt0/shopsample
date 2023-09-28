package com.pacbittencourt.shop.ui.viewmodel

import com.pacbittencourt.shop.domain.model.Order
import com.pacbittencourt.shop.domain.usecase.GetOrderDetailsUseCase
import com.pacbittencourt.shop.ui.orderdetails.OrderDetailsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderDetailsViewModelTest {

    private val getOrderDetailsUseCase: GetOrderDetailsUseCase = mockk(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `should return order details to state`() = runTest {
        every { getOrderDetailsUseCase(any()) } returns flow {
            emit(Order(items = emptyList(), orderPrice = 0.0))
        }
        val viewModel =
            OrderDetailsViewModel(getOrderDetailsUseCase, testDispatcher)

        viewModel.loadOrderDetails("1")
        advanceUntilIdle()

        val resultState = viewModel.detailsState.value
        assert(!resultState.isLoading)
        assert(resultState.orderDetailsUi != null)
        assert(resultState.orderDetailsUi?.items?.isEmpty() ?: false)
    }
}