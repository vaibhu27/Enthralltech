package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Data.model.model.User;
import com.example.myapplication.Data.model.repository.UserRepository;

public class SignupActivity extends AppCompatActivity {

    private EditText etMobile, etMpin1, etMpin2, etMpin3, etMpin4, etConfirmMpin1, etConfirmMpin2, etConfirmMpin3, etConfirmMpin4;
    private Button btnSignup;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userRepository = new UserRepository(this);

        etMobile = findViewById(R.id.etMobile);
        etMpin1 = findViewById(R.id.etMpin1);
        etMpin2 = findViewById(R.id.etMpin2);
        etMpin3 = findViewById(R.id.etMpin3);
        etMpin4 = findViewById(R.id.etMpin4);
        etConfirmMpin1 = findViewById(R.id.etConfirmMpin1);
        etConfirmMpin2 = findViewById(R.id.etConfirmMpin2);
        etConfirmMpin3 = findViewById(R.id.etConfirmMpin3);
        etConfirmMpin4 = findViewById(R.id.etConfirmMpin4);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignup();
            }
        });
    }

    private void handleSignup() {
        String mobile = etMobile.getText().toString().trim();
        String mpin = etMpin1.getText().toString() + etMpin2.getText().toString() + etMpin3.getText().toString() + etMpin4.getText().toString();
        String confirmMpin = etConfirmMpin1.getText().toString() + etConfirmMpin2.getText().toString() + etConfirmMpin3.getText().toString() + etConfirmMpin4.getText().toString();

        if (mobile.isEmpty() || mpin.isEmpty() || confirmMpin.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mpin.equals(confirmMpin)) {
            Toast.makeText(this, "M-PINs do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(mobile, mpin);
        userRepository.addUser(user);
        Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show();
        finish();
    }
}
