package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.financetracker.R;

public class BudgetFragment extends Fragment {

    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.budget, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Step 1: Connect "Add Goal" button to navigate to AddGoalFragment
        View btnAddGoal = view.findViewById(R.id.btn_add_goal);
        if (btnAddGoal != null) {
            btnAddGoal.setOnClickListener(v -> {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, AddGoalFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            });
        }

        setupNavigation(view);
    }

    private void setupNavigation(View view) {
        // Modular Navigation: Handle bottom bar clicks
        View navHome = view.findViewById(R.id.nav_home);
        if (navHome != null) {
            navHome.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        View navProfile = view.findViewById(R.id.nav_profile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, AccountFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}
