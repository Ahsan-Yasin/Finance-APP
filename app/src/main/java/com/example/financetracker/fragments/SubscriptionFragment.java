package com.example.financetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financetracker.R;
import com.example.financetracker.adapters.SubscriptionAdapter;
import com.example.financetracker.models.Subscription;
import com.example.financetracker.viewmodel.FinanceViewModel;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionFragment extends Fragment implements SubscriptionAdapter.OnSubscriptionClickListener {

    private List<Subscription> subscriptionList = new ArrayList<>();
    private SubscriptionAdapter adapter;
    private FinanceViewModel viewModel;

    public static SubscriptionFragment newInstance() {
        return new SubscriptionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // F4: Use ViewModel to bridge Kotlin Coroutines for Java
        viewModel = new ViewModelProvider(requireActivity()).get(FinanceViewModel.class);
        return inflater.inflate(R.layout.subscription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView(view);
        observeViewModel();
        viewModel.loadSubscriptions(); // F3: Persistent Read
        
        setupNavigation(view);
        setupFragmentResultListener();
    }

    private void setupRecyclerView(View view) {
        RecyclerView rvSubscriptions = view.findViewById(R.id.rv_subscriptions);
        if (rvSubscriptions != null) {
            adapter = new SubscriptionAdapter(subscriptionList, this);
            rvSubscriptions.setLayoutManager(new LinearLayoutManager(getContext()));
            rvSubscriptions.setAdapter(adapter);
        }
    }

    private void observeViewModel() {
        // Observe LiveData from the ViewModel (Safe for Java Fragments)
        viewModel.getSubscriptions().observe(getViewLifecycleOwner(), subs -> {
            subscriptionList.clear();
            subscriptionList.addAll(subs);
            adapter.notifyDataSetChanged();
        });
    }

    private void setupFragmentResultListener() {
        getParentFragmentManager().setFragmentResultListener("subscription_added", getViewLifecycleOwner(), (requestKey, result) -> {
            viewModel.loadSubscriptions(); // Refresh list via ViewModel
        });
    }

    private void setupNavigation(View view) {
        View btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        View btnAddSubscription = view.findViewById(R.id.btn_add_subscription);
        if (btnAddSubscription != null) {
            btnAddSubscription.setOnClickListener(v ->
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, AddSubscriptionFragment.newInstance())
                            .addToBackStack(null)
                            .commit()
            );
        }
    }

    @Override
    public void onSubscriptionClick(Subscription subscription) {
        Toast.makeText(getContext(), subscription.getName(), Toast.LENGTH_SHORT).show();
    }
}
