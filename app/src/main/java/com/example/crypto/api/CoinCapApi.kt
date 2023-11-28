package com.example.crypto.api

import com.example.crypto.model.HistoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinCapApi {
    @GET("assets/{symbol}/history?interval=d1")
    suspend fun getCoinCapHistory(@Path("symbol") symbol: String): HistoryResponse
}
