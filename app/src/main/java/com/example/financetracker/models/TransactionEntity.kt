package com.example.financetracker.models

data class TransactionEntity(
    val id: Int,
    val title: String,
    val amount: Double,
    val categoryId: Int
)
