package com.example.financetracker.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.financetracker.R;
import com.example.financetracker.models.Transaction;

/**
 * F2 — Receives a Parcelable Transaction object via Bundle and displays its details.
 * Uses fragment_transaction_detail.xml with uniquely-named IDs (tv_detail_title, etc.)
 */
public class TransactionDetailFragment extends Fragment {

    private static final String ARG_TRANSACTION = "selected_transaction";
    private Transaction transaction;

    /** Factory method: bundles the Parcelable Transaction (F2 requirement). */
    public static TransactionDetailFragment newInstance(Transaction transaction) {
        TransactionDetailFragment fragment = new TransactionDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TRANSACTION, transaction); // F2: Bundle with Parcelable object
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transaction = getArguments().getParcelable(ARG_TRANSACTION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Use the dedicated clean detail layout (not the full static transaction.xml screen)
        return inflater.inflate(R.layout.fragment_transaction_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Back navigation
        View ivBack = view.findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        // Bind transaction data to the UI
        if (transaction != null) {
            TextView tvIcon     = view.findViewById(R.id.tv_detail_icon);
            TextView tvTitle    = view.findViewById(R.id.tv_detail_title);
            TextView tvSubtitle = view.findViewById(R.id.tv_detail_subtitle);
            TextView tvAmount   = view.findViewById(R.id.tv_detail_amount);
            TextView tvDate     = view.findViewById(R.id.tv_detail_date);

            if (tvIcon != null)     tvIcon.setText(transaction.getIcon());
            if (tvTitle != null)    tvTitle.setText(transaction.getTitle());
            if (tvSubtitle != null) tvSubtitle.setText(
                    transaction.getCategory() + " • " + transaction.getAccount());
            if (tvDate != null)     tvDate.setText(transaction.getDate());

            if (tvAmount != null) {
                boolean income = transaction.isIncome();
                String amountStr = (income ? "+" : "-") + "$"
                        + String.format("%.2f", transaction.getAmount());
                tvAmount.setText(amountStr);
                tvAmount.setTextColor(income
                        ? Color.parseColor("#0F9D58")
                        : Color.parseColor("#C62828"));
            }
        }
    }
}
