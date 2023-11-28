package com.example.crypto.viewmodel


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto.model.CurrencyResponse
import com.example.crypto.network.CurrencyApiRetrofit

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    val apikey = "cur_live_5YMreVUw9fGL18kwGKQTQzsBk8czR2BG8uBKVAqJ"
    private val _convertedAmount = mutableStateOf("0.00")
    val convertedAmount: State<String> = _convertedAmount

    private val _convertedoneCoinAmount = mutableStateOf("0.00")
    val convertedOneCoinAmount: State<String> = _convertedoneCoinAmount

    private val _currencyStateFlow = MutableStateFlow<CurrencyResponse?>(null)
    val currencyStateFlow: StateFlow<CurrencyResponse?> = _currencyStateFlow

    private var _currencypriceStateFlow = MutableStateFlow<CurrencyResponse?>(null)
    var currencypriceStateFlow: StateFlow<CurrencyResponse?> = _currencypriceStateFlow

    fun getCurrencyRates() {
        viewModelScope.launch {
            try {
                val response = CurrencyApiRetrofit.currencyApiService.getCurrencyRates(
                    apiKey = apikey,
                    fromCurrency = "USD",
                    toCurrency = "SEK",
                    amount = 0
                )
                _currencyStateFlow.value = response
                _currencypriceStateFlow.value = response

            } catch (e: Exception) {
                Log.i("getCurrencyRates", e.printStackTrace().toString())
            }
        }
    }

    fun convertCryptoCoinToSek(amountInUSD: Double) {
        val currencyState = currencypriceStateFlow.value
        val sekExchangeRate = currencyState?.data?.get("SEK")?.value ?: 1.0
        val convertedAmount = amountInUSD * sekExchangeRate
        _convertedoneCoinAmount.value = String.format("%.2f", convertedAmount)

    }

    fun convertToSEK(amountInUSD: Double) {
        val currencyState = currencyStateFlow.value
        val sekExchangeRate = currencyState?.data?.get("SEK")?.value ?: 1.0
        val convertedAmount = amountInUSD * sekExchangeRate
        _convertedAmount.value = String.format("%.2f", convertedAmount)
    }

}