package com.example.crypto.api

import com.example.crypto.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("latest")
    suspend fun getCurrencyRates(
        @Query("apikey") apiKey: String,
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String,
        @Query("amount") amount: Int
    ): CurrencyResponse
}