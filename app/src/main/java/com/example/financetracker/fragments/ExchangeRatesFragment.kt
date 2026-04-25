package com.example.financetracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.adapters.ExchangeRateAdapter
import com.example.financetracker.models.CurrencyItem
import com.example.financetracker.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExchangeRatesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance(): ExchangeRatesFragment {
            return ExchangeRatesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exchange_rates, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchRates()
    }

    private fun fetchRates() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.instance.getLatestRates()
                
                val currencyItems = response.rates.map { 
                    CurrencyItem(it.key, it.value) 
                }
                
                withContext(Dispatchers.Main) {
                    recyclerView.adapter = ExchangeRateAdapter(currencyItems)
                    Toast.makeText(context, "Loaded ${currencyItems.size} rates successfully!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ExchangeRates", "Error fetching rates", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Network Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
