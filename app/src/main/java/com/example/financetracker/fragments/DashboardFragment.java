package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
        viewModel = new ViewModelProvider(requireActivity()).get(FinanceViewModel.class);
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
        setupNavigation(view);

        viewModel.loadTransactions();
    }

    private void setupRecyclerView() {
        adapter = new TransactionAdapter(transactionList, this);
        if (rvTransactions != null) {
            rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTransactions.setAdapter(adapter);
        }
    }

    private void observeViewModel() {
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

    private void setupNavigation(View view) {
        // --- ADD TRANSACTION BUTTON (+) ---
        View btnAdd = view.findViewById(R.id.btn_add_transaction);
        if (btnAdd != null) {
            btnAdd.setOnClickListener(v -> navigateTo(AddTransactionFragment.newInstance()));
        }

        // --- WALLETS NAVIGATION ---
        View navBudget = view.findViewById(R.id.nav_budget);
        if (navBudget != null) {
            navBudget.setOnClickListener(v -> navigateTo(SubscriptionFragment.newInstance()));
        }

        View balanceCard = view.findViewById(R.id.balance_card);
        if (balanceCard != null) {
            balanceCard.setOnClickListener(v -> navigateTo(AccountFragment.newInstance()));
        }

        // --- OTHER TABS ---
        View navTrends = view.findViewById(R.id.nav_trends);
        if (navTrends != null) {
            navTrends.setOnClickListener(v -> navigateTo(AnalyticsFragment.newInstance()));
        }

        View navProfile = view.findViewById(R.id.nav_profile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> navigateTo(AccountFragment.newInstance()));
        }
    }

    private void navigateTo(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onTransactionClick(Transaction transaction) {
        navigateTo(TransactionDetailFragment.newInstance(transaction));
    }
}
