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
import com.example.financetracker.adapters.SubscriptionAdapter;
import com.example.financetracker.models.Subscription;
import com.example.financetracker.models.Debt;
import com.example.financetracker.viewmodel.FinanceViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SubscriptionFragment extends Fragment implements SubscriptionAdapter.OnSubscriptionClickListener {

    private List<Subscription> subscriptionList = new ArrayList<>();
    private SubscriptionAdapter adapter;
    private FinanceViewModel viewModel;
    private TextView tvMonthlySubsTotal, tvTotalDebt, tvDebtListPreview;

    public static SubscriptionFragment newInstance() {
        return new SubscriptionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(FinanceViewModel.class);
        return inflater.inflate(R.layout.subscription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvMonthlySubsTotal = view.findViewWithTag("tv_subs_total");
        tvTotalDebt = view.findViewWithTag("tv_debt_total");
        tvDebtListPreview = view.findViewWithTag("tv_debt_list_preview");

        setupRecyclerView(view);
        observeViewModel();
        
        viewModel.loadSubscriptions();
        viewModel.loadDebts();
        
        setupNavigation(view);
        setupFragmentResultListener();
    }

    private void setupRecyclerView(View view) {
        RecyclerView rvSubscriptions = view.findViewById(R.id.rv_subscriptions);
        if (rvSubscriptions != null) {
            adapter = new SubscriptionAdapter(subscriptionList, this);
            rvSubscriptions.setLayoutManager(new LinearLayoutManager(getContext()));
            rvSubscriptions.setAdapter(adapter);
        }
    }

    private void observeViewModel() {
        viewModel.getSubscriptions().observe(getViewLifecycleOwner(), subs -> {
            subscriptionList.clear();
            subscriptionList.addAll(subs);
            adapter.notifyDataSetChanged();
        });

        viewModel.getDebts().observe(getViewLifecycleOwner(), debts -> {
            if (tvDebtListPreview != null) {
                StringBuilder sb = new StringBuilder();
                for (Debt debt : debts) {
                    sb.append(debt.getName()).append(": $").append(String.format(Locale.US, "%.2f", debt.getAmount())).append("\n");
                }
                tvDebtListPreview.setText(debts.isEmpty() ? "No debts yet" : sb.toString());
            }
        });

        viewModel.getTotalSubscriptionAmount().observe(getViewLifecycleOwner(), total -> {
            if (tvMonthlySubsTotal != null) tvMonthlySubsTotal.setText(String.format(Locale.US, "$%.2f", total));
        });

        viewModel.getTotalDebtAmount().observe(getViewLifecycleOwner(), total -> {
            if (tvTotalDebt != null) tvTotalDebt.setText(String.format(Locale.US, "$%.2f", total));
        });
    }

    private void setupFragmentResultListener() {
        getParentFragmentManager().setFragmentResultListener("subscription_added", getViewLifecycleOwner(), (requestKey, result) -> {
            viewModel.loadSubscriptions();
            viewModel.loadDebts();
        });
    }

    private void setupNavigation(View view) {
        View btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        View btnAddSubscription = view.findViewById(R.id.btn_add_subscription);
        if (btnAddSubscription != null) {
            btnAddSubscription.setOnClickListener(v ->
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, AddSubscriptionFragment.newInstance())
                            .addToBackStack(null)
                            .commit()
            );
        }
    }

    @Override
    public void onSubscriptionClick(Subscription subscription) {
        Toast.makeText(getContext(), subscription.getName(), Toast.LENGTH_SHORT).show();
    }
}
