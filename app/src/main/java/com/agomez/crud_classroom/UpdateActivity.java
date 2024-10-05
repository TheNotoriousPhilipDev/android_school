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

public class UpdateActivity extends AppCompatActivity {

    private EditText cedula, username, password, email;
    private Spinner role;
    private Button submitUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        cedula = findViewById(R.id.cedula);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        submitUpdate = findViewById(R.id.submitUpdate);
        role = findViewById(R.id.role);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter);

        submitUpdate.setOnClickListener(v -> {
            if (!cedula.getText().toString().trim().isEmpty()) {
                new Modificar(UpdateActivity.this).execute();
            } else {
                Toast.makeText(UpdateActivity.this, "Cedula is required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean modificar() {
        String url = Constants.URL + "classroom/update.php";
        List<Map.Entry<String, String>> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry<>("cedula", cedula.getText().toString().trim()));
        params.add(new AbstractMap.SimpleEntry<>("username", username.getText().toString().trim()));
        params.add(new AbstractMap.SimpleEntry<>("password", password.getText().toString().trim()));
        params.add(new AbstractMap.SimpleEntry<>("email", email.getText().toString().trim()));
        return APIHandler.POST(url, params);
    }

    class Modificar extends AsyncTask<String, String, String> {
        private Activity context;
        Modificar(Activity context) {
            this.context = context;
        }
        protected String doInBackground(String... params) {
            if (modificar())
                context.runOnUiThread(() -> Toast.makeText(context, "User updated successfully", Toast.LENGTH_LONG).show());
            else
                context.runOnUiThread(() -> Toast.makeText(context, "Failed to update user", Toast.LENGTH_LONG).show());
            return null;
        }
    }
}