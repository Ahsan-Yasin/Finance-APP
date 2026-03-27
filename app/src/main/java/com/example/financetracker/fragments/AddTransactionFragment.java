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

        btnSave.setOnClickListener(saveListener);
        btnQuickSave.setOnClickListener(saveListener);
    }
}
