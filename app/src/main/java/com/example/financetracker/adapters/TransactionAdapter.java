package com.example.financetracker.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financetracker.R;
import com.example.financetracker.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> implements Filterable {

    private List<Transaction> transactionList;
    private List<Transaction> transactionListFull;
    private OnTransactionClickListener listener;

    public interface OnTransactionClickListener {
        void onTransactionClick(Transaction transaction);
    }

    public TransactionAdapter(List<Transaction> transactionList, OnTransactionClickListener listener) {
        this.transactionList = transactionList;
        this.transactionListFull = new ArrayList<>(transactionList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.bind(transaction, listener);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    @Override
    public Filter getFilter() {
        return transactionFilter;
    }

    private Filter transactionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Transaction> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(transactionListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Transaction item : transactionListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern) || 
                        item.getCategory().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            transactionList.clear();
            transactionList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tvIcon, tvTitle, tvSubtitle, tvAmount;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIcon = itemView.findViewById(R.id.tv_icon);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSubtitle = itemView.findViewById(R.id.tv_subtitle);
            tvAmount = itemView.findViewById(R.id.tv_amount);
        }

        public void bind(final Transaction transaction, final OnTransactionClickListener listener) {
            tvIcon.setText(transaction.getIcon());
            tvTitle.setText(transaction.getTitle());
            tvSubtitle.setText(transaction.getCategory() + " • " + transaction.getAccount());
            
            String amountStr = (transaction.isIncome() ? "+" : "-") + "$" + String.format("%.2f", transaction.getAmount());
            tvAmount.setText(amountStr);
            tvAmount.setTextColor(transaction.isIncome() ? Color.parseColor("#0F9D58") : Color.parseColor("#111111"));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onTransactionClick(transaction);
                    }
                }
            });
        }
    }
}
