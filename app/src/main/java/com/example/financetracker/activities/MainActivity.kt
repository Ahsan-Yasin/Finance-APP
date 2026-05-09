package com.example.financetracker.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.financetracker.R
import com.example.financetracker.fragments.AccountFragment
import com.example.financetracker.fragments.AnalyticsFragment
import com.example.financetracker.fragments.DashboardFragment
import com.example.financetracker.fragments.SubscriptionFragment
import com.example.financetracker.ui.LoginActivity
import com.example.financetracker.ui.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private var userEmail: String = "user@example.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userEmail = intent.getStringExtra("USER_EMAIL") ?: FirebaseAuth.getInstance().currentUser?.email ?: "user@example.com"
        bottomNav = findViewById(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            loadFragment(DashboardFragment.newInstance(userEmail))
            bottomNav.selectedItemId = R.id.navigation_home
        }

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
                    // Navigate to the new Compose Profile Screen
                    startActivity(Intent(this, ProfileActivity::class.java))
                    false 
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                // PART 1.6 - Logout and redirect
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
