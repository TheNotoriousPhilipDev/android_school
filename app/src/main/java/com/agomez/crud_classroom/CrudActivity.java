package com.agomez.crud_classroom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CrudActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(CrudActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        Button createButton = findViewById(R.id.createButton);
        Button readButton = findViewById(R.id.readButton);
        Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        createButton.setOnClickListener(v -> {
            Intent intent = new Intent(CrudActivity.this, CreateActivity.class);
            startActivity(intent);
        });

        readButton.setOnClickListener(v -> {
            Intent intent = new Intent(CrudActivity.this, ReadActivity.class);
            startActivity(intent);
        });

        updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(CrudActivity.this, UpdateActivity.class);
            startActivity(intent);
        });

        deleteButton.setOnClickListener(v -> {
            Intent intent = new Intent(CrudActivity.this, DeleteActivity.class);
            startActivity(intent);
        });
    }
}