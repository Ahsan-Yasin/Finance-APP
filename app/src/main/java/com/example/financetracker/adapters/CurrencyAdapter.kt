package com.example.financetracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.models.CurrencyItem

class CurrencyAdapter(private val currencyList: List<CurrencyItem>) : 
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCode: TextView = view.findViewById(R.id.tv_currency_code)
        val tvRate: TextView = view.findViewById(R.id.tv_currency_rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = currencyList[position]
        holder.tvCode.text = item.currencyCode
        holder.tvRate.text = String.format("%.4f", item.rate)
    }

    override fun getItemCount() = currencyList.size
}
