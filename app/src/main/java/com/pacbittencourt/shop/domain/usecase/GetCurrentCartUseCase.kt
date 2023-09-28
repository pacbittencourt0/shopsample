package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.domain.model.ItemOrder
import kotlinx.coroutines.flow.Flow

interface GetCurrentCartUseCase {
    operator fun invoke(): Flow<List<ItemOrder>>
}