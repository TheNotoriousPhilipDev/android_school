package com.agomez.crud_classroom;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button submitRegister = findViewById(R.id.submitRegister);

        submitRegister.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            // Handle registration logic here
        });
    }
}