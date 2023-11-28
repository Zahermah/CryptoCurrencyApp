package com.example.crypto.model

sealed class Results {
    object Initial : Results()
    data class Success(val exchangeRates: ExchangeRatesResponse) : Results()
    data class Error(val message: String) : Results()
}