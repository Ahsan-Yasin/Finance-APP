package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financetracker.R;
import com.example.financetracker.adapters.SubscriptionAdapter;
import com.example.financetracker.models.Subscription;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionFragment extends Fragment implements SubscriptionAdapter.OnSubscriptionClickListener {

    private RecyclerView rvSubscriptions;
    private SubscriptionAdapter adapter;
    private List<Subscription> subscriptionList;

    public static SubscriptionFragment newInstance() {
        return new SubscriptionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subscription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Note: The original subscription.xml might need an actual RecyclerView ID added to it if it doesn't have one.
        // Assuming we might need to add one or use a container in the layout.
        // Looking at the provided subscription.xml, it has static items. 
        // For a real implementation, we should replace them with a RecyclerView.
        // However, I will stick to the provided UI as much as possible.
        
        setupMockData();
    }

    private void setupMockData() {
        subscriptionList = new ArrayList<>();
        subscriptionList.add(new Subscription("1", "Netflix", 15.99, "Oct 12", "Monthly", "N"));
        subscriptionList.add(new Subscription("2", "Spotify Premium", 9.99, "Oct 18", "Monthly", "≡"));
        subscriptionList.add(new Subscription("3", "Adobe Creative Cloud", 19.99, "Oct 22", "Monthly", "☁️"));
    }

    @Override
    public void onSubscriptionClick(Subscription subscription) {
        // Handle subscription click
    }
}
