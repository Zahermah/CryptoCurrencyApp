package com.example.crypto.network

import com.example.crypto.api.CurrencyApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CurrencyApiRetrofit {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.currencyapi.com/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val currencyApiService = retrofit.create(CurrencyApiService::class.java)
}