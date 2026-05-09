package com.example.financetracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.financetracker.R
import com.example.financetracker.database.DatabaseHelper
import com.example.financetracker.models.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionsDbFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var searchInput: EditText
    private lateinit var searchBtn: Button
    private lateinit var addBtn: Button
    private lateinit var tvResults: TextView

    companion object {
        fun newInstance(): TransactionsDbFragment {
            return TransactionsDbFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transactions_db, container, false)
        dbHelper = DatabaseHelper(requireContext())
        
        searchInput = view.findViewById(R.id.input_search)
        searchBtn = view.findViewById(R.id.btn_search)
        addBtn = view.findViewById(R.id.btn_add_dummy)
        tvResults = view.findViewById(R.id.tv_results)
        
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAllData()

        searchBtn.setOnClickListener {
            val query = searchInput.text.toString()
            if (query.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val searchResults = dbHelper.searchTransactionsByTitle(query)
                    val resultStr = searchResults.joinToString("\n") { "${it.id}: ${it.title} - $${it.amount}" }
                    
                    withContext(Dispatchers.Main) {
                        tvResults.text = if (searchResults.isEmpty()) "No results found" else resultStr
                        Toast.makeText(context, "Found ${searchResults.size} results!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                loadAllData()
            }
        }

        addBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val suffix = System.currentTimeMillis() % 1000
                val newId = dbHelper.insertTransaction("Groceries $suffix", (10..100).random().toDouble(), 1)
                
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Added transaction ID: $newId", Toast.LENGTH_SHORT).show()
                    loadAllData()
                }
            }
        }
    }

    private fun loadAllData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val transactions = dbHelper.getAllTransactions()
            val resultStr = transactions.joinToString("\n") { "${it.id}: ${it.title} - $${it.amount}" }
            
            withContext(Dispatchers.Main) {
                tvResults.text = if (transactions.isEmpty()) "Database empty" else resultStr
            }
        }
    }
}
