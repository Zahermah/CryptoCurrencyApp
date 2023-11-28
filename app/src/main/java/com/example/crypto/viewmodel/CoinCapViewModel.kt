package com.example.crypto.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto.model.CryptoCurrency
import com.example.crypto.model.HistoryData
import com.example.crypto.network.ApiClient
import com.example.crypto.network.CoinCapApiService.coinCapApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CoinCapViewModel : ViewModel() {
    private val _historyData = MutableStateFlow<List<HistoryData>>(emptyList())
    val historyData: StateFlow<List<HistoryData>> = _historyData.asStateFlow()

    private val _assets = MutableStateFlow<List<CryptoCurrency>>(emptyList())
    val assets: StateFlow<List<CryptoCurrency>> get() = _assets

    init{
        fetchDataCoinCap()

    }

    fun fetchCoinCapHistory(symbol: String) {
        viewModelScope.launch {
            try {
                val response = coinCapApi.getCoinCapHistory(symbol)
                _historyData.value = response.data
                Log.i("CoinCapViewModel", response.data.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchDataCoinCap() {
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.getAssets()
                _assets.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("CryptoViewModel", e.printStackTrace().toString())
            }
        }
    }
}