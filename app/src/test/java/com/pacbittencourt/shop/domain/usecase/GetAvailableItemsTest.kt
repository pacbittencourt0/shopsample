package com.pacbittencourt.shop.domain.usecase

import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetAvailableItemsTest {

    @Test
    fun `should return all available items`() = runTest {

        val useCase = GetAvailableItemsImpl()

        val result = useCase()
        result.collect {
            assert(it.isNotEmpty())
        }
    }
}