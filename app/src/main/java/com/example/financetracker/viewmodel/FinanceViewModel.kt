package com.example.financetracker.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.financetracker.database.DatabaseHelper
import com.example.financetracker.models.TransactionEntity
import com.example.financetracker.models.Subscription
import com.example.financetracker.models.Debt
import com.google.firebase.auth.FirebaseAuth
import com.smd.financeTracker.firestore.FirestoreHelper
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

    private val _totalSubscriptionAmount = MutableLiveData<Double>()
    fun getTotalSubscriptionAmount(): LiveData<Double> = _totalSubscriptionAmount

    private val _totalDebtAmount = MutableLiveData<Double>()
    fun getTotalDebtAmount(): LiveData<Double> = _totalDebtAmount

    init {
        // PART 2 — Sync data from Firestore in real-time
        syncWithFirestore()
    }

    private fun syncWithFirestore() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            // Sync transactions from Firestore to local (or just observe)
            firestoreHelper.syncUserTransactions(userId).collectLatest { firestoreData ->
                // Here you would typically update local DB with Firestore data
                // For A04 simplicity, we'll assume Firestore is the source of truth for sync
                loadTransactions()
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

    fun loadDebtSummary() {
        viewModelScope.launch {
            _totalDebtAmount.postValue(dbHelper.getTotalDebt())
        }
    }

    fun addTransaction(title: String, amount: Double, catId: Int) {
        viewModelScope.launch {
            val id = dbHelper.insertTransaction(title, amount, catId)
            loadTransactions()
            
            // PART 2 — Save to Firestore
            val userId = auth.currentUser?.uid ?: return@launch
            val transactionData = mapOf(
                "title" to title,
                "amount" to amount,
                "categoryId" to catId,
                "timestamp" to System.currentTimeMillis()
            )
            firestoreHelper.saveData("users/$userId/transactions", id.toString(), transactionData)
        }
    }

    fun addSubscription(sub: Subscription) {
        viewModelScope.launch {
            dbHelper.insertSubscription(sub)
            loadSubscriptions()
            
            // PART 2 — Save to Firestore
            val userId = auth.currentUser?.uid ?: return@launch
            firestoreHelper.saveData("users/$userId/subscriptions", sub.id, sub)
        }
    }

    fun addDebt(name: String, amount: Double, desc: String, type: String) {
        viewModelScope.launch {
            dbHelper.insertDebt(name, amount, desc, type)
            loadDebtSummary()
            
            // PART 2 — Save to Firestore
            val userId = auth.currentUser?.uid ?: return@launch
            val debtData = mapOf("name" to name, "amount" to amount, "desc" to desc, "type" to type)
            firestoreHelper.saveData("users/$userId/debts", System.currentTimeMillis().toString(), debtData)
        }
    }
}
