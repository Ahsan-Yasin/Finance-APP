package com.example.financetracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.models.CurrencyItem

class ExchangeRateAdapter(private val rates: List<CurrencyItem>) : RecyclerView.Adapter<ExchangeRateAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currencyCodeText: TextView = view.findViewById(R.id.tv_currency_code)
        val rateText: TextView = view.findViewById(R.id.tv_rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exchange_rate, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = rates[position]
        holder.currencyCodeText.text = item.currencyCode
        holder.rateText.text = item.rate.toString()
    }

    override fun getItemCount() = rates.size
}
