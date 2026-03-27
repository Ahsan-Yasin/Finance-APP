package com.example.financetracker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.financetracker.R
import com.example.financetracker.fragments.DashboardFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val userEmail = intent.getStringExtra("USER_EMAIL") ?: "User"
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DashboardFragment.newInstance(userEmail))
                 .commit()
        }
    }
}
