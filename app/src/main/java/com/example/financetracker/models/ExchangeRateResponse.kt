package com.example.financetracker.models

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    val amount: Double,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

data class CurrencyItem(
    val currencyCode: String,
    val rate: Double
)
