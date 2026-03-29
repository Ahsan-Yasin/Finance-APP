package com.example.financetracker;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

/**
 * Minimal stub activity for adding a subscription.
 * Declared in AndroidManifest.xml so launching it does not throw ActivityNotFoundException.
 * Full UI is handled by AddSubscriptionFragment; this Activity is kept for architectural flexibility.
 */
public class AddSubscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Finish immediately and delegate to Fragment-based flow
        Toast.makeText(this, "Use the Subscriptions tab to add a subscription.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
