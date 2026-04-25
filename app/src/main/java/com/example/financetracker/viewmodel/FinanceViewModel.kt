package com.example.financetracker.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.financetracker.database.DatabaseHelper
import com.example.financetracker.models.TransactionEntity
import com.example.financetracker.models.Subscription
import com.example.financetracker.models.Debt
import kotlinx.coroutines.launch

class FinanceViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)

    private val _transactions = MutableLiveData<List<TransactionEntity>>()
    fun getTransactions(): LiveData<List<TransactionEntity>> = _transactions

    private val _subscriptions = MutableLiveData<List<Subscription>>()
    fun getSubscriptions(): LiveData<List<Subscription>> = _subscriptions

    private val _debts = MutableLiveData<List<Debt>>()
    fun getDebts(): LiveData<List<Debt>> = _debts

    private val _totalSubscriptionAmount = MutableLiveData<Double>()
    fun getTotalSubscriptionAmount(): LiveData<Double> = _totalSubscriptionAmount

    private val _totalDebtAmount = MutableLiveData<Double>()
    fun getTotalDebtAmount(): LiveData<Double> = _totalDebtAmount

    fun loadTransactions() {
        viewModelScope.launch {
            _transactions.postValue(dbHelper.getAllTransactions())
        }
    }

    fun loadSubscriptions() {
        viewModelScope.launch {
            val subs = dbHelper.getAllSubscriptions()
            _subscriptions.postValue(subs)
            _totalSubscriptionAmount.postValue(subs.sumOf { it.amount })
        }
    }

    fun loadDebts() {
        viewModelScope.launch {
            val debts = dbHelper.getAllDebts()
            _debts.postValue(debts)
            _totalDebtAmount.postValue(debts.sumOf { it.amount })
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

    fun addDebt(name: String, amount: Double, desc: String, type: String) {
        viewModelScope.launch {
            dbHelper.insertDebt(name, amount, desc, type)
            loadDebts()
        }
    }
}
