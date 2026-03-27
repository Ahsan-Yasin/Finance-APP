package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.financetracker.R;
import com.example.financetracker.models.Transaction;

public class TransactionDetailFragment extends Fragment {

    private static final String ARG_TRANSACTION = "selected_transaction";
    private Transaction transaction;

    public static TransactionDetailFragment newInstance(Transaction transaction) {
        TransactionDetailFragment fragment = new TransactionDetailFragment();
        Bundle args = new Bundle();
        // Requirement F5 & Step 2: Passing Parcelable object in Bundle
        args.putParcelable(ARG_TRANSACTION, transaction);
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Using transaction.xml as the base for the detail view
        return inflater.inflate(R.layout.transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Binding UI elements from transaction.xml
        ImageView ivBack = view.findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        if (transaction != null) {
            // Update the UI with the transaction details
            // Note: transaction.xml has mock items (Item 1, Item 2...), 
            // in a real detail screen we would show specific fields for ONE transaction.
            
            // For now, let's map the first mock item's fields to our data
            TextView tvTitle = view.findViewById(R.id.tv_coffee_title);
            TextView tvSubtitle = view.findViewById(R.id.tv_coffee_sub);
            TextView tvAmount = view.findViewWithTag("amount_tag"); // Using tag if ID isn't unique or clear
            
            if (tvTitle != null) tvTitle.setText(transaction.getTitle());
            if (tvSubtitle != null) tvSubtitle.setText(transaction.getCategory() + " • " + transaction.getAccount());
            
            // Logic to hide other mock items to focus on this detail
            View otherItems = view.findViewById(R.id.item2_content);
            if (otherItems != null) ((ViewGroup)otherItems.getParent()).setVisibility(View.GONE);
        }
    }
}
