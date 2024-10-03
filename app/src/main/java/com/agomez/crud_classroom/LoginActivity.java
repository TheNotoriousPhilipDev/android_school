package com.agomez.crud_classroom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button submitLogin = findViewById(R.id.submitLogin);
        Button registerButton = findViewById(R.id.registerButton);

        submitLogin.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            // Handle login logic here
            // For example, you can start MainActivity and pass the username
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", user);
            startActivity(intent);
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}