package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.financetracker.R;

public class AnalyticsFragment extends Fragment {

    public static AnalyticsFragment newInstance() {
        return new AnalyticsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.analytics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Null-safe back button: analytics.xml may or may not have btn_back depending on design iteration
        View btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        // --- NEW CLICKS TO MAKE EVERYTHING INTERACTIVE ---

        View btnCalendar = view.findViewById(R.id.btn_calendar);
        if (btnCalendar != null) btnCalendar.setOnClickListener(v -> showToast("Open Calendar"));

        // Time Filters
        View tabWeek = view.findViewById(R.id.tab_week);
        if (tabWeek != null) tabWeek.setOnClickListener(v -> showToast("Filter by Week"));

        View tabMonth = view.findViewById(R.id.tab_month);
        if (tabMonth != null) tabMonth.setOnClickListener(v -> showToast("Filter by Month"));

        View tabYear = view.findViewById(R.id.tab_year);
        if (tabYear != null) tabYear.setOnClickListener(v -> showToast("Filter by Year"));

        // Spending details
        View chartContainer = view.findViewById(R.id.chart_container);
        if (chartContainer != null) chartContainer.setOnClickListener(v -> showToast("Category Spending Details"));

        View tvViewDetailsCashflow = view.findViewById(R.id.tv_view_details_cashflow);
        if (tvViewDetailsCashflow != null) tvViewDetailsCashflow.setOnClickListener(v -> showToast("Weekly Cash Flow Details"));

        // Spending Items
        View cardExpense1 = view.findViewById(R.id.card_expense_1);
        if (cardExpense1 != null) cardExpense1.setOnClickListener(v -> showToast("Apple Store Details"));

        View cardExpense2 = view.findViewById(R.id.card_expense_2);
        if (cardExpense2 != null) cardExpense2.setOnClickListener(v -> showToast("Whole Foods Details"));

        View cardExpense3 = view.findViewById(R.id.card_expense_3);
        if (cardExpense3 != null) cardExpense3.setOnClickListener(v -> showToast("Uber Trip Details"));

        // Mock Bottom Navigation
        View mockNavHome = view.findViewById(R.id.mock_nav_home);
        if (mockNavHome != null) mockNavHome.setOnClickListener(v -> navTo(DashboardFragment.newInstance(null)));

        View mockNavAnalytics = view.findViewById(R.id.mock_nav_analytics);
        if (mockNavAnalytics != null) mockNavAnalytics.setOnClickListener(v -> showToast("Already on Analytics"));

        View mockNavWallets = view.findViewById(R.id.mock_nav_wallets);
        if (mockNavWallets != null) mockNavWallets.setOnClickListener(v -> navTo(SubscriptionFragment.newInstance()));

        View mockNavSettings = view.findViewById(R.id.mock_nav_settings);
        if (mockNavSettings != null) mockNavSettings.setOnClickListener(v -> navTo(AccountFragment.newInstance()));
    }

    private void showToast(String message) {
        if (getContext() != null) {
            android.widget.Toast.makeText(getContext(), message, android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    private void navTo(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
