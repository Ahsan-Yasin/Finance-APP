package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.financetracker.R;
import com.example.financetracker.viewmodel.FinanceViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class AddTransactionFragment extends Fragment {

    private EditText etAmount;
    private MaterialButton btnSave;
    private ImageView ivClose;
    private MaterialCardView btnQuickSave;
    private FinanceViewModel viewModel;
    private String selectedCategory = "General";

    public static AddTransactionFragment newInstance() {
        return new AddTransactionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(FinanceViewModel.class);
        return inflater.inflate(R.layout.add_transcation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etAmount = view.findViewById(R.id.et_amount);
        btnSave = view.findViewById(R.id.btn_save_transaction);
        ivClose = view.findViewById(R.id.iv_close);
        btnQuickSave = view.findViewById(R.id.btn_quick_save);

        ivClose.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        View.OnClickListener saveListener = v -> {
            String amountStr = etAmount.getText().toString();
            if (amountStr.isEmpty() || amountStr.equals("0.00")) {
                Toast.makeText(getContext(), "Please enter an amount", Toast.LENGTH_SHORT).show();
            } else {
                double amount = Double.parseDouble(amountStr);
                viewModel.addTransaction(selectedCategory + " Purchase", amount, 1);
                getParentFragmentManager().setFragmentResult("add_transaction_request", new Bundle());
                Toast.makeText(getContext(), "Transaction Saved!", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack();
            }
        };

        if (btnSave != null) btnSave.setOnClickListener(saveListener);
        if (btnQuickSave != null) btnQuickSave.setOnClickListener(saveListener);

        setupCategoryClickListeners(view);
    }

    private void setupCategoryClickListeners(View view) {
        MaterialCardView food = view.findViewById(R.id.card_category_food);
        MaterialCardView transport = view.findViewById(R.id.card_category_transport);
        MaterialCardView shopping = view.findViewById(R.id.card_category_shopping);
        MaterialCardView rent = view.findViewById(R.id.card_category_rent);
        MaterialCardView health = view.findViewById(R.id.card_category_health);
        MaterialCardView others = view.findViewById(R.id.card_category_others);

        View.OnClickListener categoryListener = v -> {
            // Reset all strokes first
            food.setStrokeWidth(1);
            transport.setStrokeWidth(1);
            shopping.setStrokeWidth(1);
            rent.setStrokeWidth(1);
            health.setStrokeWidth(1);
            others.setStrokeWidth(1);

            // Highlight selected
            MaterialCardView selected = (MaterialCardView) v;
            selected.setStrokeWidth(4);
            selected.setStrokeColor(getResources().getColor(R.color.primary));

            if (v.getId() == R.id.card_category_food) selectedCategory = "Food";
            else if (v.getId() == R.id.card_category_transport) selectedCategory = "Transport";
            else if (v.getId() == R.id.card_category_shopping) selectedCategory = "Shopping";
            else if (v.getId() == R.id.card_category_rent) selectedCategory = "Rent";
            else if (v.getId() == R.id.card_category_health) selectedCategory = "Health";
            else selectedCategory = "Others";

            Toast.makeText(getContext(), selectedCategory + " Selected", Toast.LENGTH_SHORT).show();
        };

        if (food != null) food.setOnClickListener(categoryListener);
        if (transport != null) transport.setOnClickListener(categoryListener);
        if (shopping != null) shopping.setOnClickListener(categoryListener);
        if (rent != null) rent.setOnClickListener(categoryListener);
        if (health != null) health.setOnClickListener(categoryListener);
        if (others != null) others.setOnClickListener(categoryListener);
    }
}
