package com.example.crypto.network

import com.example.crypto.api.CoinCapApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoinCapApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.coincap.io/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val coinCapApi: CoinCapApi = retrofit.create(CoinCapApi::class.java)
}