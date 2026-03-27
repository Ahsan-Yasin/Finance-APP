package com.example.financetracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financetracker.R;
import com.example.financetracker.models.Subscription;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder> {

    private List<Subscription> subscriptionList;
    private OnSubscriptionClickListener listener;

    public interface OnSubscriptionClickListener {
        void onSubscriptionClick(Subscription subscription);
    }

    public SubscriptionAdapter(List<Subscription> subscriptionList, OnSubscriptionClickListener listener) {
        this.subscriptionList = subscriptionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscription, parent, false);
        return new SubscriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {
        Subscription subscription = subscriptionList.get(position);
        holder.bind(subscription, listener);
    }

    @Override
    public int getItemCount() {
        return subscriptionList.size();
    }

    static class SubscriptionViewHolder extends RecyclerView.ViewHolder {
        TextView tvIcon, tvName, tvDueDate, tvAmount, tvCycle;

        public SubscriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIcon = itemView.findViewById(R.id.tv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDueDate = itemView.findViewById(R.id.tv_due_date);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvCycle = itemView.findViewById(R.id.tv_cycle);
        }

        public void bind(final Subscription subscription, final OnSubscriptionClickListener listener) {
            tvIcon.setText(subscription.getIcon());
            tvName.setText(subscription.getName());
            tvDueDate.setText("Next due: " + subscription.getDueDate());
            tvAmount.setText("$" + String.format("%.2f", subscription.getAmount()));
            tvCycle.setText(subscription.getBillingCycle().toUpperCase());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onSubscriptionClick(subscription);
                    }
                }
            });
        }
    }
}
