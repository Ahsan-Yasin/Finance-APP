package com.example.financetracker.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.financetracker.database.DatabaseHelper
import com.example.financetracker.firestore.FirestoreHelper
import com.example.financetracker.models.TransactionEntity
import com.example.financetracker.models.Subscription
import com.example.financetracker.models.Debt
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class FinanceViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)
    private val firestoreHelper = FirestoreHelper()
    private val auth = FirebaseAuth.getInstance()

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

    init {
        syncWithFirestore()
    }

    private fun syncWithFirestore() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            firestoreHelper.syncUserData("users/$userId/transactions", Map::class.java).collectLatest {
                loadTransactions()
                loadDebtSummary()
            }
        }
    }

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

    fun loadDebtSummary() {
        viewModelScope.launch {
            _totalDebtAmount.postValue(dbHelper.getTotalDebt())
        }
    }

    fun addTransaction(title: String, amount: Double, catId: Int) {
        viewModelScope.launch {
            val id = dbHelper.insertTransaction(title, amount, catId)
            loadTransactions()
            val userId = auth.currentUser?.uid ?: return@launch
            firestoreHelper.saveData("users/$userId/transactions", id.toString(), mapOf("title" to title, "amount" to amount))
        }
    }

    fun addSubscription(sub: Subscription) {
        viewModelScope.launch {
            dbHelper.insertSubscription(sub)
            loadSubscriptions()
            val userId = auth.currentUser?.uid ?: return@launch
            firestoreHelper.saveData("users/$userId/subscriptions", sub.id, sub)
        }
    }

    fun addDebt(name: String, amount: Double, desc: String, type: String) {
        viewModelScope.launch {
            dbHelper.insertDebt(name, amount, desc, type)
            loadDebts()
            val userId = auth.currentUser?.uid ?: return@launch
            firestoreHelper.saveData("users/$userId/debts", System.currentTimeMillis().toString(), mapOf("name" to name, "amount" to amount))
        }
    }

    fun resetData() {
        dbHelper.resetDatabase()
        loadTransactions()
        loadSubscriptions()
        loadDebts()
        loadDebtSummary()
    }
}
