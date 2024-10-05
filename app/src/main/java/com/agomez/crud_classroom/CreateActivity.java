package com.agomez.crud_classroom;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class CreateActivity extends AppCompatActivity {

    private EditText cedula, username, password, email;
    private Spinner role;
    private Button submitCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        cedula = findViewById(R.id.cedula);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        role = findViewById(R.id.role);
        submitCreate = findViewById(R.id.submitCreate);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter);

        submitCreate.setOnClickListener(v -> {
            if (!cedula.getText().toString().trim().isEmpty()) {
                new Insertar(CreateActivity.this).execute();
            } else {
                Toast.makeText(CreateActivity.this, "Cedula is required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean insertar() {
        String url = Constants.URL + "classroom/add.php";
        List<Map.Entry<String, String>> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry<>("cedula", cedula.getText().toString().trim()));
        params.add(new AbstractMap.SimpleEntry<>("username", username.getText().toString().trim()));
        params.add(new AbstractMap.SimpleEntry<>("password", password.getText().toString().trim()));
        params.add(new AbstractMap.SimpleEntry<>("email", email.getText().toString().trim()));
        params.add(new AbstractMap.SimpleEntry<>("role", role.getSelectedItem().toString().trim()));
        return APIHandler.POST(url, params);
    }

    class Insertar extends AsyncTask<String, String, String> {
        private Activity context;
        Insertar(Activity context) {
            this.context = context;
        }
        protected String doInBackground(String... params) {
            if (insertar())
                context.runOnUiThread(() -> {
                    Toast.makeText(context, "User created successfully", Toast.LENGTH_LONG).show();
                    cedula.setText("");
                    username.setText("");
                    password.setText("");
                    email.setText("");
                });
            else
                context.runOnUiThread(() -> Toast.makeText(context, "Failed to create user", Toast.LENGTH_LONG).show());
            return null;
        }
    }
}