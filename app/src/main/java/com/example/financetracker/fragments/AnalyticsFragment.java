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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.analytics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Connect Back Button
        view.findViewById(R.id.btn_back).setOnClickListener(v -> getParentFragmentManager().popBackStack());
        
        // Other analytics logic would go here (Requirement L6/L7 concepts)
    }
}
