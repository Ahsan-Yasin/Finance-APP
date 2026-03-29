package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.financetracker.R;
import com.google.android.material.button.MaterialButton;

public class AddSubscriptionFragment extends Fragment {

    private EditText etServiceName, etAmount;
    private MaterialButton btnSave;
    private ImageView ivBack;

    public static AddSubscriptionFragment newInstance() {
        return new AddSubscriptionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etServiceName = view.findViewById(R.id.tabs_layout).findViewWithTag("service_name"); // Assuming tags or specific IDs
        
        ivBack = view.findViewById(R.id.iv_back);
        btnSave = view.findViewById(R.id.btn_save);

        if (ivBack != null) ivBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Subscription Saved!", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack();
            });
        }

        // --- NEW CLICKS TO MAKE EVERYTHING INTERACTIVE ---

        // Tabs
        View tabSubscription = view.findViewById(R.id.tab_subscription);
        if (tabSubscription != null) tabSubscription.setOnClickListener(v -> Toast.makeText(getContext(), "Subscription Tab Selected", Toast.LENGTH_SHORT).show());

        View tabDebtIou = view.findViewById(R.id.tab_debt_iou);
        if (tabDebtIou != null) tabDebtIou.setOnClickListener(v -> Toast.makeText(getContext(), "Debt/IOU Tab Selected", Toast.LENGTH_SHORT).show());

        // Billing Cycle Toggles
        View toggleMonthly = view.findViewById(R.id.toggle_monthly);
        if (toggleMonthly != null) toggleMonthly.setOnClickListener(v -> Toast.makeText(getContext(), "Monthly Billing Selected", Toast.LENGTH_SHORT).show());

        View toggleYearly = view.findViewById(R.id.toggle_yearly);
        if (toggleYearly != null) toggleYearly.setOnClickListener(v -> Toast.makeText(getContext(), "Yearly Billing Selected", Toast.LENGTH_SHORT).show());
    }
}
