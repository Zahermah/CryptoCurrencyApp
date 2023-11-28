package com.example.crypto.api

import com.example.crypto.model.AssetCrypto
import retrofit2.http.GET

interface ApiService {
    @GET("assets")
    suspend fun getAssets(): AssetCrypto
}
