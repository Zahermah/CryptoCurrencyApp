package com.example.crypto.model

data class CurrencyResponse(
    val meta: Meta,
    val data: Map<String, Currency>
)

data class Meta(
    val last_updated_at: String
)

data class Currency(
    val code: String,
    val value: Double
)
