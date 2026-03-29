package com.example.financetracker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.financetracker.R
import com.example.financetracker.fragments.AccountFragment
import com.example.financetracker.fragments.AnalyticsFragment
import com.example.financetracker.fragments.DashboardFragment
import com.example.financetracker.fragments.SubscriptionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private var userEmail: String = "user@example.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // F1 — Receive USER_EMAIL from LoginActivity Intent Extra
        userEmail = intent.getStringExtra("USER_EMAIL") ?: "user@example.com"

        bottomNav = findViewById(R.id.bottom_navigation)

        // Load default fragment only on first creation (not on rotation)
        if (savedInstanceState == null) {
            loadFragment(DashboardFragment.newInstance(userEmail))
            bottomNav.selectedItemId = R.id.navigation_home
        }

        // F4 — Fragment Transactions: switch fragments without restarting the Activity
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    loadFragment(DashboardFragment.newInstance(userEmail))
                    true
                }
                R.id.navigation_analytics -> {
                    loadFragment(AnalyticsFragment.newInstance())
                    true
                }
                R.id.navigation_wallets -> {
                    loadFragment(SubscriptionFragment.newInstance())
                    true
                }
                R.id.navigation_settings -> {
                    loadFragment(AccountFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Replaces the fragment container content with the given fragment.
     * Does NOT add to back stack so BottomNav tabs feel like top-level destinations.
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
