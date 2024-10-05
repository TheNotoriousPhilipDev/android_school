package com.agomez.crud_classroom;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button submitLogin, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        submitLogin = findViewById(R.id.submitLogin);
        registerButton = findViewById(R.id.registerButton);

        submitLogin.setOnClickListener(v -> new LoginTask().execute());
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    private class LoginTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String url = Constants.URL + "classroom/get-by-id.php";
            List<Map.Entry<String, String>> params = new ArrayList<>();
            params.add(new AbstractMap.SimpleEntry<>("username", username.getText().toString().trim()));
            params.add(new AbstractMap.SimpleEntry<>("password", password.getText().toString().trim()));

            // Log the URL and parameters
            Log.d("LoginTask", "URL: " + url);
            for (Map.Entry<String, String> param : params) {
                Log.d("LoginTask", "Param: " + param.getKey() + " = " + param.getValue());
            }

            return APIHandler.POSTRESPONSE(url, params);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    Log.d("LoginTask", "Response: " + result);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray usersArray = jsonObject.optJSONArray("usuarios");
                    if (usersArray != null && usersArray.length() > 0) {
                        JSONObject userObject = usersArray.getJSONObject(0);
                        String role = userObject.getString("role");
                        if (role.equals("ADMIN")) {
                            Intent intent = new Intent(LoginActivity.this, CrudActivity.class);
                            intent.putExtra("username", username.getText().toString());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("username", username.getText().toString());
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Student not permitted", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Error connecting to server", Toast.LENGTH_LONG).show();
            }
        }
    }
}