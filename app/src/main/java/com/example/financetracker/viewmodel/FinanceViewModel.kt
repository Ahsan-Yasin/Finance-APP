package com.example.financetracker.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.financetracker.database.DatabaseHelper
import com.example.financetracker.models.TransactionEntity
import com.example.financetracker.models.Subscription
import kotlinx.coroutines.launch

class FinanceViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

    // F3: Transactions LiveData
    private val _transactions = MutableLiveData<List<TransactionEntity>>()
    fun getTransactions(): LiveData<List<TransactionEntity>> = _transactions

    // F3: Subscriptions LiveData
    private val _subscriptions = MutableLiveData<List<Subscription>>()
    fun getSubscriptions(): LiveData<List<Subscription>> = _subscriptions

    fun loadTransactions() {
        viewModelScope.launch {
            _transactions.postValue(dbHelper.getAllTransactions())
        }
    }

    fun loadSubscriptions() {
        viewModelScope.launch {
            _subscriptions.postValue(dbHelper.getAllSubscriptions())
        }
    }

    fun addTransaction(title: String, amount: Double, catId: Int) {
        viewModelScope.launch {
            dbHelper.insertTransaction(title, amount, catId)
            loadTransactions()
        }
    }

    fun addSubscription(sub: Subscription) {
        viewModelScope.launch {
            dbHelper.insertSubscription(sub)
            loadSubscriptions()
        }
    }
}
