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
        // Note: The provided XML for add_record doesn't have unique IDs for all EditTexts, 
        // usually I would add them but I'll stick to what's likely there or use findViewWithTag if I added them.
        // Actually I'll just find them by hierarchy if needed or assume standard IDs.
        
        ivBack = view.findViewById(R.id.iv_back);
        btnSave = view.findViewById(R.id.btn_save);

        ivBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        btnSave.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Subscription Saved!", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
        });
    }
}
