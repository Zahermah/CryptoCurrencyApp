package com.example.crypto.api

import android.content.Context
import java.util.*

object ApiKeyProvider {
    fun getApiKey(context: Context): String {
        val properties = Properties()
        val assets = context.assets.open("local.properties")
        properties.load(assets)
        return properties.getProperty("API_KEY") ?: throw IllegalStateException("API key not found")
    }
}