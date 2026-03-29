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
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financetracker.R;
import com.example.financetracker.adapters.TransactionAdapter;
import com.example.financetracker.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements TransactionAdapter.OnTransactionClickListener {

    private RecyclerView rvTransactions;
    private TransactionAdapter adapter;
    private List<Transaction> transactionList;
    private SearchView searchView;
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
        return inflater.inflate(R.layout.dashbaord, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUsername = view.findViewById(R.id.tv_username);
        rvTransactions = view.findViewById(R.id.rv_transactions);
        searchView = view.findViewById(R.id.search_view);

        if (getArguments() != null) {
            String email = getArguments().getString("user_email", "User");
            if (tvUsername != null) tvUsername.setText(email.split("@")[0]);
        }

        setupRecyclerView();
        setupSearchView();
        setupFragmentResultListener();
        setupNavigation(view);
    }

    private void setupRecyclerView() {
        transactionList = new ArrayList<>();
        transactionList.add(new Transaction("1", "Blue Bottle Coffee", "Food & Drinks", "Chase Debit", 12.50, "OCT 24", "🍴", false));
        transactionList.add(new Transaction("2", "Amazon.com", "Shopping", "Apple Card", 84.20, "OCT 24", "🛒", false));
        transactionList.add(new Transaction("3", "Salary Deposit", "Income", "Chase Savings", 4250.00, "OCT 23", "💵", true));
        transactionList.add(new Transaction("4", "Uber Trip", "Transport", "Chase Debit", 24.15, "OCT 23", "🚗", false));

        adapter = new TransactionAdapter(transactionList, this);
        if (rvTransactions != null) {
            rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTransactions.setAdapter(adapter);
        }
    }

    private void setupSearchView() {
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    adapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
    }

    private void setupFragmentResultListener() {
        getParentFragmentManager().setFragmentResultListener("add_transaction_request", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Transaction newTransaction = result.getParcelable("new_transaction");
                if (newTransaction != null) {
                    transactionList.add(0, newTransaction);
                    adapter.notifyItemInserted(0);
                    if (rvTransactions != null) rvTransactions.scrollToPosition(0);
                    Toast.makeText(getContext(), "Transaction Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupNavigation(View view) {
        // Center Floating Action Button
        View btnAddTransaction = view.findViewById(R.id.btn_add_transaction);
        if (btnAddTransaction != null) {
            btnAddTransaction.setOnClickListener(v -> navigateTo(AddTransactionFragment.newInstance()));
        }

        // Wallets/Budget Navigation
        View balanceCard = view.findViewById(R.id.balance_card);
        if (balanceCard != null) {
            balanceCard.setOnClickListener(v -> navigateTo(SubscriptionFragment.newInstance()));
        }

        View navBudget = view.findViewById(R.id.nav_budget);
        if (navBudget != null) {
            navBudget.setOnClickListener(v -> navigateTo(SubscriptionFragment.newInstance()));
        }

        // Profile Navigation
        View profileCard = view.findViewById(R.id.profile_card);
        if (profileCard != null) {
            profileCard.setOnClickListener(v -> navigateTo(AccountFragment.newInstance()));
        }

        View navProfile = view.findViewById(R.id.nav_profile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> navigateTo(AccountFragment.newInstance()));
        }

        // Stats/Trends
        View navTrends = view.findViewById(R.id.nav_trends);
        if (navTrends != null) {
            navTrends.setOnClickListener(v -> navigateTo(AnalyticsFragment.newInstance()));
        }

        // Home
        View navHome = view.findViewById(R.id.nav_home);
        if (navHome != null) {
            navHome.setOnClickListener(v -> Toast.makeText(getContext(), "Already on Home", Toast.LENGTH_SHORT).show());
        }

        // Income / Expenses
        View layoutIncome = view.findViewById(R.id.layout_income);
        if (layoutIncome != null) {
            layoutIncome.setOnClickListener(v -> Toast.makeText(getContext(), "Income Details", Toast.LENGTH_SHORT).show());
        }

        View layoutExpense = view.findViewById(R.id.layout_expense);
        if (layoutExpense != null) {
            layoutExpense.setOnClickListener(v -> Toast.makeText(getContext(), "Expense Details", Toast.LENGTH_SHORT).show());
        }

        // Notifications
        View btnNotifications = view.findViewById(R.id.btn_notifications);
        if (btnNotifications != null) {
            btnNotifications.setOnClickListener(v -> Toast.makeText(getContext(), "No new notifications", Toast.LENGTH_SHORT).show());
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
