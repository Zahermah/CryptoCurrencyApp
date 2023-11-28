package com.example.crypto.model

data class AssetCrypto(
    val data: List<CryptoCurrency>
)

data class CryptoCurrency(
    var id: String?,
    var rank: String?,
    var symbol: String?,
    var name: String?,
    var supply: String?,
    var maxSupply: String?,
    var marketCapUsd: String?,
    var volumeUsd24Hr: String?,
    var priceUsd: String?,
    var changePercent24Hr: String?,
    var vwap24Hr: String?,
    var explorer: String?
)