package com.example.financetracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financetracker.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailLayout, passwordLayout;
    private TextInputEditText emailEditText, passwordEditText;
    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.login);
        } catch (Exception e) {
            // If the layout fails to load, the app would normally crash silently
            e.printStackTrace();
            return;
        }

        emailLayout = findViewById(R.id.emailInputLayout);
        passwordLayout = findViewById(R.id.passwordInputLayout);
        
        if (emailLayout != null) {
            emailEditText = (TextInputEditText) emailLayout.getEditText();
        }
        if (passwordLayout != null) {
            passwordEditText = (TextInputEditText) passwordLayout.getEditText();
        }
        
        btnLogin = findViewById(R.id.btnLogin);

        if (btnLogin != null) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (emailEditText == null || passwordEditText == null) {
                        Toast.makeText(LoginActivity.this, "UI Binding Error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();

                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("USER_EMAIL", email);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, "Navigation Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }
}
