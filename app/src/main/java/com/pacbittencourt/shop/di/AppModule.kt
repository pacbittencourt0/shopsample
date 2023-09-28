package com.pacbittencourt.shop.di

import com.pacbittencourt.shop.domain.usecase.AddItemUseCase
import com.pacbittencourt.shop.domain.usecase.AddItemUseCaseImpl
import com.pacbittencourt.shop.domain.usecase.ApplyDiscountUseCase
import com.pacbittencourt.shop.domain.usecase.ApplyDiscountUseCaseImpl
import com.pacbittencourt.shop.domain.usecase.ClearCartUseCase
import com.pacbittencourt.shop.domain.usecase.ClearCartUseCaseImpl
import com.pacbittencourt.shop.domain.usecase.DeleteItemUseCase
import com.pacbittencourt.shop.domain.usecase.DeleteItemUseCaseImpl
import com.pacbittencourt.shop.domain.usecase.GetAvailableItems
import com.pacbittencourt.shop.domain.usecase.GetAvailableItemsImpl
import com.pacbittencourt.shop.domain.usecase.GetCurrentCartUseCase
import com.pacbittencourt.shop.domain.usecase.GetCurrentCartUseCaseImpl
import com.pacbittencourt.shop.domain.usecase.GetOrderDetailsUseCase
import com.pacbittencourt.shop.domain.usecase.GetOrderDetailsUseCaseImpl
import com.pacbittencourt.shop.domain.usecase.GetOrdersUseCase
import com.pacbittencourt.shop.domain.usecase.GetOrdersUseCaseImpl
import com.pacbittencourt.shop.domain.usecase.PlaceOrderUseCase
import com.pacbittencourt.shop.domain.usecase.PlaceOrderUseCaseImpl
import com.pacbittencourt.shop.ui.cart.CartViewModel
import com.pacbittencourt.shop.ui.orderdetails.OrderDetailsViewModel
import com.pacbittencourt.shop.ui.orders.OrdersViewModel
import com.pacbittencourt.shop.ui.products.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        CartViewModel(
            placeOrderUseCase = get(),
            getCurrentCartUseCase = get(),
            clearCart = get(),
            deleteItemUseCase = get(),
            applyDiscountUseCase = get()
        )
    }
    viewModel {
        ProductsViewModel(
            getAvailableItems = get(),
            addItemUseCase = get()
        )
    }
    viewModel {
        OrdersViewModel(
            getOrdersUseCase = get()
        )
    }
    viewModel {
        OrderDetailsViewModel(
            getOrderDetailsUseCase = get()
        )
    }
}

val useCaseModule = module {
    single<PlaceOrderUseCase> { PlaceOrderUseCaseImpl() }
    single<GetCurrentCartUseCase> { GetCurrentCartUseCaseImpl() }
    single<ClearCartUseCase> { ClearCartUseCaseImpl() }
    single<GetOrderDetailsUseCase> { GetOrderDetailsUseCaseImpl() }
    single<GetOrdersUseCase> { GetOrdersUseCaseImpl() }
    single<GetAvailableItems> { GetAvailableItemsImpl() }
    single<AddItemUseCase> { AddItemUseCaseImpl() }
    single<DeleteItemUseCase> { DeleteItemUseCaseImpl() }
    single<ApplyDiscountUseCase> { ApplyDiscountUseCaseImpl() }
}