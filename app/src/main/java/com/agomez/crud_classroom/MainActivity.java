package com.agomez.crud_classroom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        TextView welcomeMessage = findViewById(R.id.welcomeMessage);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Optionally, handle the username passed from LoginActivity
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            welcomeMessage.setText("Welcome, " + username);
        }
    }
}