package com.pacbittencourt.shop.ui.viewmodel

import com.pacbittencourt.shop.domain.model.Item
import com.pacbittencourt.shop.domain.usecase.AddItemUseCase
import com.pacbittencourt.shop.domain.usecase.GetAvailableItems
import com.pacbittencourt.shop.ui.products.ProductsViewModel
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
class ProductsViewModelTest {

    private val getAvailableItems: GetAvailableItems = mockk()
    private val addItemUseCase: AddItemUseCase = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `should get all items available when start`() = runTest {
        val item = Item(1, "", 1.0)
        every { getAvailableItems() } returns flow { emit(listOf(item)) }

        val viewModel = ProductsViewModel(getAvailableItems, addItemUseCase, testDispatcher)

        advanceUntilIdle()
        val resultState = viewModel.itemsState.value
        assert(!resultState.isLoading)
        assert(resultState.items.size == 1)
        assert(resultState.items[0].id == 1)
        assert(resultState.items[0].description == "")
        assert(resultState.items[0].price == "1,00")
    }

    @Test
    fun `should add item to cart when request`() = runTest {
        val item = Item(1, "", 1.0)
        every { getAvailableItems() } returns flow { emit(listOf(item)) }
        every { addItemUseCase(any(), any()) } returns true

        val viewModel = ProductsViewModel(getAvailableItems, addItemUseCase, testDispatcher)
        advanceUntilIdle()

        viewModel.addItemToCart(1, 2.0)

        verify { addItemUseCase(1, 2.0) }
    }
}