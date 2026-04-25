package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financetracker.R;
import com.example.financetracker.adapters.TransactionAdapter;
import com.example.financetracker.models.Transaction;
import com.example.financetracker.models.TransactionEntity;
import com.example.financetracker.viewmodel.FinanceViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements TransactionAdapter.OnTransactionClickListener {

    private RecyclerView rvTransactions;
    private TransactionAdapter adapter;
    private List<Transaction> transactionList = new ArrayList<>();
    private FinanceViewModel viewModel;
    private TextView tvUsername;

    public static DashboardFragment newInstance(String email) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString("user_email", email);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // bridge F4: ViewModel handles threading and database natively
        viewModel = new ViewModelProvider(this).get(FinanceViewModel.class);
        return inflater.inflate(R.layout.dashbaord, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUsername = view.findViewById(R.id.tv_username);
        rvTransactions = view.findViewById(R.id.rv_transactions);

        if (getArguments() != null) {
            String email = getArguments().getString("user_email", "User");
            if (tvUsername != null) tvUsername.setText(email.split("@")[0]);
        }

        setupRecyclerView();
        observeViewModel();
        viewModel.loadTransactions(); // F3: Initial Load
        
        setupFragmentResultListener();
    }

    private void setupRecyclerView() {
        adapter = new TransactionAdapter(transactionList, this);
        if (rvTransactions != null) {
            rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTransactions.setAdapter(adapter);
        }
    }

    private void observeViewModel() {
        // Observation logic is safe for Java
        viewModel.getTransactions().observe(getViewLifecycleOwner(), entities -> {
            transactionList.clear();
            for (TransactionEntity entity : entities) {
                transactionList.add(new Transaction(
                    String.valueOf(entity.getId()),
                    entity.getTitle(),
                    "General",
                    "Cash",
                    entity.getAmount(),
                    "Today",
                    "💰",
                    entity.getAmount() > 0
                ));
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void setupFragmentResultListener() {
        getParentFragmentManager().setFragmentResultListener("add_transaction_request", getViewLifecycleOwner(), (requestKey, result) -> {
            viewModel.loadTransactions(); // Refresh via ViewModel
        });
    }

    @Override
    public void onTransactionClick(Transaction transaction) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, TransactionDetailFragment.newInstance(transaction))
                .addToBackStack(null)
                .commit();
    }
}
