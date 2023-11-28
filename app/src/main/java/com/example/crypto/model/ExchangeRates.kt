package com.example.crypto.model

import com.google.gson.annotations.SerializedName

data class ExchangeRates(
    val rates: Map<String, Double>,
    val base: String,
    val date: String
)

data class ExchangeRatesResponse(
    @SerializedName("timestamp")
    val timestamp: Long,

    @SerializedName("base_currency")
    val baseCurrency: String,

    @SerializedName("rates")
    val rates: Map<String, CurrencyRate>,

    @SerializedName("value")
    val value: Double
)
data class CurrencyRate(
    @SerializedName("code")
    val code: String,

    @SerializedName("value")
    val value: Double
)