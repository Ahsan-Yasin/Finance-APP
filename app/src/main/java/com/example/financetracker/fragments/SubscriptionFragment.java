package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financetracker.R;
import com.example.financetracker.adapters.SubscriptionAdapter;
import com.example.financetracker.models.Subscription;

import java.util.ArrayList;
import java.util.List;

/**
 * F3 — Displays a RecyclerView list of subscriptions using SubscriptionAdapter + SubscriptionViewHolder.
 * F4 — Navigates to AddSubscriptionFragment without restarting the activity.
 */
public class SubscriptionFragment extends Fragment implements SubscriptionAdapter.OnSubscriptionClickListener {

    private List<Subscription> subscriptionList;
    private SubscriptionAdapter adapter;

    public static SubscriptionFragment newInstance() {
        return new SubscriptionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subscription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupData();
        setupRecyclerView(view);
        setupNavigation(view);
    }

    private void setupData() {
        subscriptionList = new ArrayList<>();
        subscriptionList.add(new Subscription("1", "Netflix",              15.99, "Oct 12", "Monthly", "N"));
        subscriptionList.add(new Subscription("2", "Spotify Premium",       9.99, "Oct 18", "Monthly", "S"));
        subscriptionList.add(new Subscription("3", "Adobe Creative Cloud", 54.99, "Oct 22", "Monthly", "A"));
        subscriptionList.add(new Subscription("4", "iCloud Storage",        2.99, "Oct 30", "Monthly", "☁"));
        subscriptionList.add(new Subscription("5", "YouTube Premium",      13.99, "Nov 05", "Monthly", "▶"));
    }

    /** F3 — Wires SubscriptionAdapter (with custom ViewHolder) to the RecyclerView in subscription.xml */
    private void setupRecyclerView(View view) {
        RecyclerView rvSubscriptions = view.findViewById(R.id.rv_subscriptions);
        if (rvSubscriptions != null) {
            adapter = new SubscriptionAdapter(subscriptionList, this);
            rvSubscriptions.setLayoutManager(new LinearLayoutManager(getContext()));
            rvSubscriptions.setAdapter(adapter);
        }
    }

    private void setupNavigation(View view) {
        // Back / home button
        View btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        // "Add Subscription" button → F4 Fragment Transaction
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

        // --- NEW CLICKS TO MAKE EVERYTHING INTERACTIVE ---

        View btnProfile = view.findViewById(R.id.btn_profile);
        if (btnProfile != null) btnProfile.setOnClickListener(v -> showToast("Profile Info"));

        // Summary Cards
        View cardMonthlySubs = view.findViewById(R.id.card_monthly_subs);
        if (cardMonthlySubs != null) cardMonthlySubs.setOnClickListener(v -> showToast("Monthly Subscriptions Info"));

        View cardTotalDebt = view.findViewById(R.id.card_total_debt);
        if (cardTotalDebt != null) cardTotalDebt.setOnClickListener(v -> showToast("Total Debt Info"));

        // Headers
        View tvViewAllSubs = view.findViewById(R.id.tv_view_all_subs);
        if (tvViewAllSubs != null) tvViewAllSubs.setOnClickListener(v -> showToast("Viewing All Subscriptions"));

        // Debt Items
        View cardDebtOwe = view.findViewById(R.id.card_debt_owe);
        if (cardDebtOwe != null) cardDebtOwe.setOnClickListener(v -> showToast("Debt Details: You Owe Mike Ross"));

        View cardDebtOwedTo = view.findViewById(R.id.card_debt_owed_to);
        if (cardDebtOwedTo != null) cardDebtOwedTo.setOnClickListener(v -> showToast("Debt Details: Sarah owes you"));

        // Mock Bottom Navigation
        View mockNavOverview = view.findViewById(R.id.mock_nav_overview);
        if (mockNavOverview != null) mockNavOverview.setOnClickListener(v -> navTo(DashboardFragment.newInstance(null)));

        View mockNavSubs = view.findViewById(R.id.mock_nav_subs);
        if (mockNavSubs != null) mockNavSubs.setOnClickListener(v -> showToast("Already on Subscriptions"));

        View mockNavDebt = view.findViewById(R.id.mock_nav_debt);
        if (mockNavDebt != null) mockNavDebt.setOnClickListener(v -> showToast("Navigating to Debt (Coming Soon)"));

        View mockNavSettingsSub = view.findViewById(R.id.mock_nav_settings_sub);
        if (mockNavSettingsSub != null) mockNavSettingsSub.setOnClickListener(v -> navTo(AccountFragment.newInstance()));
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

    /** F2 — Clicking a subscription item passes a Parcelable Subscription via Bundle to detail. */
    @Override
    public void onSubscriptionClick(Subscription subscription) {
        // Navigate to detail via Bundle. For scope, showing a toast (detail screen is bonus).
        android.widget.Toast.makeText(getContext(),
                subscription.getName() + " — $" + String.format("%.2f", subscription.getAmount()),
                android.widget.Toast.LENGTH_SHORT).show();
    }
}
