package com.agomez.crud_classroom;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText cedula, username, password, email;
    private Spinner role;
    private Button submitRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        cedula = findViewById(R.id.cedula);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        role = findViewById(R.id.role);
        submitRegister = findViewById(R.id.submitRegister);

        // Set up the role spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter);

        submitRegister.setOnClickListener(v -> new RegisterTask().execute());
    }

    private class RegisterTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            String url = Constants.URL + "classroom/add.php";
            List<Map.Entry<String, String>> params = new ArrayList<>();
            params.add(new AbstractMap.SimpleEntry<>("cedula", cedula.getText().toString().trim()));
            params.add(new AbstractMap.SimpleEntry<>("username", username.getText().toString().trim()));
            params.add(new AbstractMap.SimpleEntry<>("password", password.getText().toString().trim()));
            params.add(new AbstractMap.SimpleEntry<>("email", email.getText().toString().trim()));
            params.add(new AbstractMap.SimpleEntry<>("role", role.getSelectedItem().toString().trim()));

            // Log the URL and parameters
            Log.d("RegisterTask", "URL: " + url);
            for (Map.Entry<String, String> param : params) {
                Log.d("RegisterTask", "Param: " + param.getKey() + " = " + param.getValue());
            }

            return APIHandler.POST(url, params);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}