package com.example.financetracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financetracker.R;
import com.example.financetracker.models.Goal;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    private List<Goal> goalList;

    public GoalAdapter(List<Goal> goalList) {
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_goal, parent, false);
        // Note: Using a specific item layout would be better, but I will adapt to your XML structure
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goalList.get(position);
        holder.bind(goal);
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAmount, tvDate;
        ProgressBar progressBar;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            // Binding to IDs found in budget.xml for goal cards
            tvTitle = itemView.findViewById(R.id.tv_goal_title_1);
            tvAmount = itemView.findViewById(R.id.tv_goal_amount_1);
            tvDate = itemView.findViewById(R.id.tv_goal_date_1);
            progressBar = itemView.findViewById(R.id.pb_goal_1);
        }

        public void bind(Goal goal) {
            if (tvTitle != null) tvTitle.setText(goal.getTitle());
            if (tvAmount != null) tvAmount.setText("$" + goal.getCurrentAmount());
            if (tvDate != null) tvDate.setText("Target: " + goal.getTargetDate());
            if (progressBar != null) {
                progressBar.setMax((int) goal.getTargetAmount());
                progressBar.setProgress((int) goal.getCurrentAmount());
            }
        }
    }
}
