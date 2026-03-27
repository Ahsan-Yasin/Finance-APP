package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.financetracker.R;
import com.google.android.material.button.MaterialButton;

public class AddGoalFragment extends Fragment {

    public static AddGoalFragment newInstance() {
        return new AddGoalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_goal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        MaterialButton btnCreateGoal = view.findViewById(R.id.btn_create_goal);
        if (btnCreateGoal != null) {
            btnCreateGoal.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Goal Created!", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack();
            });
        }
    }
}
