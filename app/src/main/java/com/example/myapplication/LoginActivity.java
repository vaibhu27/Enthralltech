package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {

    private EditText etMobile, etMpin1, etMpin2, etMpin3, etMpin4;
    private Button btnLogin, btnSignup;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etMobile = findViewById(R.id.etMobile);
        etMpin1 = findViewById(R.id.etMpin1);
        etMpin2 = findViewById(R.id.etMpin2);
        etMpin3 = findViewById(R.id.etMpin3);
        etMpin4 = findViewById(R.id.etMpin4);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setTextWatchers();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignup();
            }
        });

        observeLoginResult();
    }

    private void setTextWatchers() {
        etMpin1.addTextChangedListener(new PinTextWatcher(etMpin1, etMpin2));
        etMpin2.addTextChangedListener(new PinTextWatcher(etMpin2, etMpin3, etMpin1));
        etMpin3.addTextChangedListener(new PinTextWatcher(etMpin3, etMpin4, etMpin2));
        etMpin4.addTextChangedListener(new PinTextWatcher(etMpin4, null, etMpin3));
    }

    private void handleLogin() {
        String mobile = etMobile.getText().toString().trim();
        String mpin = etMpin1.getText().toString() + etMpin2.getText().toString() +
                etMpin3.getText().toString() + etMpin4.getText().toString();

        if (!validateInputs(mobile, mpin)) return;

        loginViewModel.login(mobile, mpin);
    }

    private void handleSignup() {
        String mobile = etMobile.getText().toString().trim();
        String mpin = etMpin1.getText().toString() + etMpin2.getText().toString() +
                etMpin3.getText().toString() + etMpin4.getText().toString();

        if (!validateInputs(mobile, mpin)) return;

        loginViewModel.addUser(mobile, mpin);
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

    private boolean validateInputs(String mobile, String mpin) {
        if (mobile.isEmpty() || mobile.length() != 10 || !mobile.matches("[0-9]+")) {
            Toast.makeText(this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mpin.isEmpty() || mpin.length() != 4 || !mpin.matches("[0-9]+")) {
            Toast.makeText(this, "Invalid M-PIN", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void observeLoginResult() {
        loginViewModel.getLoginResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if (success) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class PinTextWatcher implements TextWatcher {
        private View currentView;
        private View nextView;
        private View previousView;

        private PinTextWatcher(View currentView, View nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
        }

        private PinTextWatcher(View currentView, View nextView, View previousView) {
            this.currentView = currentView;
            this.nextView = nextView;
            this.previousView = previousView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            if (text.length() == 1 && nextView != null) {
                nextView.requestFocus();
            } else if (text.length() == 0 && previousView != null) {
                previousView.requestFocus();
            }
        }
    }
}
