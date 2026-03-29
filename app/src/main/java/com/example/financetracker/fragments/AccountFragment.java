package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.financetracker.R;

public class AccountFragment extends Fragment {

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Null-safe: account.xml may not always have btn_back (it's a top-level BottomNav tab)
        View btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        // FAB to open Add Transaction — null-safe in case ID is missing after layout edits
        View fabMain = view.findViewById(R.id.fab_main);
        if (fabMain != null) {
            fabMain.setOnClickListener(v -> {
                Fragment frag = AddTransactionFragment.newInstance();
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, frag)
                        .addToBackStack(null)
                        .commit();
            });
        }

        // --- NEW CLICKS TO MAKE EVERYTHING INTERACTIVE ---

        // Top bar
        View btnMore = view.findViewById(R.id.btn_more);
        if (btnMore != null) btnMore.setOnClickListener(v -> showToast("More Options"));

        // Action Buttons
        View btnTransfer = view.findViewById(R.id.btn_transfer);
        if (btnTransfer != null) btnTransfer.setOnClickListener(v -> showToast("Transfer Funds"));

        View btnAddNew = view.findViewById(R.id.btn_add_new);
        if (btnAddNew != null) btnAddNew.setOnClickListener(v -> showToast("Add New Wallet"));

        View tvViewAllWallets = view.findViewById(R.id.tv_view_all_wallets);
        if (tvViewAllWallets != null) tvViewAllWallets.setOnClickListener(v -> showToast("Viewing All Wallets"));

        // Wallet Cards
        View cardWallet1 = view.findViewById(R.id.card_wallet_1);
        if (cardWallet1 != null) cardWallet1.setOnClickListener(v -> showToast("Main Savings Details"));

        View cardWallet2 = view.findViewById(R.id.card_wallet_2);
        if (cardWallet2 != null) cardWallet2.setOnClickListener(v -> showToast("Daily Cash Details"));

        View cardWallet3 = view.findViewById(R.id.card_wallet_3);
        if (cardWallet3 != null) cardWallet3.setOnClickListener(v -> showToast("Visa Platinum Details"));

        View cardWallet4 = view.findViewById(R.id.card_wallet_4);
        if (cardWallet4 != null) cardWallet4.setOnClickListener(v -> showToast("PayPal Account Details"));

        // Upgrade Banner
        View cardUpgrade = view.findViewById(R.id.card_upgrade);
        if (cardUpgrade != null) cardUpgrade.setOnClickListener(v -> showToast("Upgrade Plan"));

        // Mock Bottom Navigation
        View mockNavHome = view.findViewById(R.id.mock_nav_home);
        if (mockNavHome != null) mockNavHome.setOnClickListener(v -> navTo(DashboardFragment.newInstance(null)));

        View mockNavWallets = view.findViewById(R.id.mock_nav_wallets);
        if (mockNavWallets != null) mockNavWallets.setOnClickListener(v -> showToast("Already on Wallets"));

        View mockNavStats = view.findViewById(R.id.mock_nav_stats);
        if (mockNavStats != null) mockNavStats.setOnClickListener(v -> navTo(AnalyticsFragment.newInstance()));

        View mockNavProfile = view.findViewById(R.id.mock_nav_profile);
        if (mockNavProfile != null) mockNavProfile.setOnClickListener(v -> showToast("Profile Settings"));
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
