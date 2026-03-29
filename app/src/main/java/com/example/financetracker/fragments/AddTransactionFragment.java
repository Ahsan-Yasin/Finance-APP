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
import com.example.financetracker.models.Transaction;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class AddTransactionFragment extends Fragment {

    private EditText etAmount;
    private MaterialButton btnSave;
    private ImageView ivClose;
    private MaterialCardView btnQuickSave;

    public static AddTransactionFragment newInstance() {
        return new AddTransactionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                // Create a new transaction object (Requirement F5)
                Transaction newTransaction = new Transaction(
                        String.valueOf(System.currentTimeMillis()),
                        "New Expense", // Mocked title
                        "General",     // Mocked category
                        "Main Wallet", // Mocked account
                        amount,
                        "TODAY",
                        "💸",
                        false
                );

                // Step 3: Send data back using Fragment Result (Requirement F3/F4)
                Bundle result = new Bundle();
                result.putParcelable("new_transaction", newTransaction);
                getParentFragmentManager().setFragmentResult("add_transaction_request", result);
                
                Toast.makeText(getContext(), "Transaction Saved!", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack();
            }
        };

        if (btnSave != null) btnSave.setOnClickListener(saveListener);
        if (btnQuickSave != null) btnQuickSave.setOnClickListener(saveListener);

        // --- NEW CLICKS TO MAKE EVERYTHING INTERACTIVE ---

        // Income/Expense Toggles
        View btnIncome = view.findViewById(R.id.btn_income);
        if (btnIncome != null) btnIncome.setOnClickListener(v -> Toast.makeText(getContext(), "Income Selected", Toast.LENGTH_SHORT).show());
        View btnExpense = view.findViewById(R.id.btn_expense);
        if (btnExpense != null) btnExpense.setOnClickListener(v -> Toast.makeText(getContext(), "Expense Selected", Toast.LENGTH_SHORT).show());

        // Categories
        View tvSeeAllCategories = view.findViewById(R.id.tv_see_all_categories);
        if (tvSeeAllCategories != null) tvSeeAllCategories.setOnClickListener(v -> Toast.makeText(getContext(), "View All Categories", Toast.LENGTH_SHORT).show());

        View cardCategoryFood = view.findViewById(R.id.card_category_food);
        if (cardCategoryFood != null) cardCategoryFood.setOnClickListener(v -> Toast.makeText(getContext(), "Food Category Selected", Toast.LENGTH_SHORT).show());
        View cardCategoryTransport = view.findViewById(R.id.card_category_transport);
        if (cardCategoryTransport != null) cardCategoryTransport.setOnClickListener(v -> Toast.makeText(getContext(), "Transport Category Selected", Toast.LENGTH_SHORT).show());
        View cardCategoryShopping = view.findViewById(R.id.card_category_shopping);
        if (cardCategoryShopping != null) cardCategoryShopping.setOnClickListener(v -> Toast.makeText(getContext(), "Shopping Category Selected", Toast.LENGTH_SHORT).show());
        View cardCategoryRent = view.findViewById(R.id.card_category_rent);
        if (cardCategoryRent != null) cardCategoryRent.setOnClickListener(v -> Toast.makeText(getContext(), "Rent Category Selected", Toast.LENGTH_SHORT).show());
        View cardCategoryHealth = view.findViewById(R.id.card_category_health);
        if (cardCategoryHealth != null) cardCategoryHealth.setOnClickListener(v -> Toast.makeText(getContext(), "Health Category Selected", Toast.LENGTH_SHORT).show());
        View cardCategoryOthers = view.findViewById(R.id.card_category_others);
        if (cardCategoryOthers != null) cardCategoryOthers.setOnClickListener(v -> Toast.makeText(getContext(), "Other Category Selected", Toast.LENGTH_SHORT).show());

        // Payment Method
        View cardPaymentMethod = view.findViewById(R.id.card_payment_method);
        if (cardPaymentMethod != null) cardPaymentMethod.setOnClickListener(v -> Toast.makeText(getContext(), "Select Payment Method", Toast.LENGTH_SHORT).show());

        // Date and Time
        View cardDatePicker = view.findViewById(R.id.card_date_picker);
        if (cardDatePicker != null) cardDatePicker.setOnClickListener(v -> Toast.makeText(getContext(), "Select Date", Toast.LENGTH_SHORT).show());
        View cardTimePicker = view.findViewById(R.id.card_time_picker);
        if (cardTimePicker != null) cardTimePicker.setOnClickListener(v -> Toast.makeText(getContext(), "Select Time", Toast.LENGTH_SHORT).show());

        // Attach Receipt
        View cardAttachReceipt = view.findViewById(R.id.card_attach_receipt);
        if (cardAttachReceipt != null) cardAttachReceipt.setOnClickListener(v -> Toast.makeText(getContext(), "Attach Receipt", Toast.LENGTH_SHORT).show());
    }
}
