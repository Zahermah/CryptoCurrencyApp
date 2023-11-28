package com.example.crypto

import com.example.crypto.viewmodel.CurrencyViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest

import org.junit.Test


class CurrencyViewModelTest {

    @Test
    fun `convertOneCoinToSek() should update the convertedOneCoinAmount`() = runTest {
        val viewModel = CurrencyViewModel()
        viewModel.convertOneCoinToSek(1.0)

        assertEquals("1,00", viewModel.convertedOneCoinAmount.value)
    }

    @Test
    fun `convertToSEK() should update the convertedAmount`() = runTest {
        val viewModel = CurrencyViewModel()
        viewModel.convertToSEK(100.0)

        assertEquals("100,00", viewModel.convertedAmount.value)
    }
}
