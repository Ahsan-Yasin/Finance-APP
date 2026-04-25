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
import androidx.lifecycle.ViewModelProvider;

import com.example.financetracker.R;
import com.example.financetracker.models.Subscription;
import com.example.financetracker.viewmodel.FinanceViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.UUID;

public class AddSubscriptionFragment extends Fragment {

    private EditText etServiceName, etAmount;
    private MaterialButton btnSave;
    private ImageView ivBack;
    private FinanceViewModel viewModel;
    private String billingCycle = "Monthly";

    public static AddSubscriptionFragment newInstance() {
        return new AddSubscriptionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(FinanceViewModel.class);
        return inflater.inflate(R.layout.add_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etServiceName = view.findViewById(R.id.et_service_name);
        etAmount = view.findViewById(R.id.et_amount);
        ivBack = view.findViewById(R.id.iv_back);
        btnSave = view.findViewById(R.id.btn_save);

        if (ivBack != null) ivBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                String name = etServiceName.getText().toString();
                String amountStr = etAmount.getText().toString();

                if (name.isEmpty() || amountStr.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    saveSubscription(name, Double.parseDouble(amountStr));
                }
            });
        }

        setupCycleToggles(view);
    }

    private void setupCycleToggles(View view) {
        View toggleMonthly = view.findViewById(R.id.toggle_monthly);
        View toggleYearly = view.findViewById(R.id.toggle_yearly);

        if (toggleMonthly != null) toggleMonthly.setOnClickListener(v -> billingCycle = "Monthly");
        if (toggleYearly != null) toggleYearly.setOnClickListener(v -> billingCycle = "Yearly");
    }

    private void saveSubscription(String name, double amount) {
        Subscription sub = new Subscription(
                UUID.randomUUID().toString(),
                name,
                amount,
                "Next Month",
                billingCycle,
                name.substring(0, 1).toUpperCase()
        );

        viewModel.addSubscription(sub);
        
        getParentFragmentManager().setFragmentResult("subscription_added", new Bundle());
        Toast.makeText(getContext(), "Subscription Saved!", Toast.LENGTH_SHORT).show();
        getParentFragmentManager().popBackStack();
    }
}
