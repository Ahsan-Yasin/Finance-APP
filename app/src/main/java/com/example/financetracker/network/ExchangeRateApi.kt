package com.example.financetracker.network

import com.example.financetracker.models.ExchangeRateResponse
import retrofit2.http.GET

interface ExchangeRateApi {
    @GET("latest")
    suspend fun getLatestRates(): ExchangeRateResponse
}
