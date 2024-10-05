package com.agomez.crud_classroom;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class ReadActivity extends AppCompatActivity {

    private Button listButton, queryButton;
    private TextView userList, userQueryResult;
    private EditText cedulaQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        listButton = findViewById(R.id.listButton);
        queryButton = findViewById(R.id.queryButton);
        userList = findViewById(R.id.userList);
        userQueryResult = findViewById(R.id.userQueryResult);
        cedulaQuery = findViewById(R.id.cedulaQuery);

        listButton.setOnClickListener(v -> new ListarUsuarios(ReadActivity.this).execute());
        queryButton.setOnClickListener(v -> {
            if (!cedulaQuery.getText().toString().trim().isEmpty()) {
                new ConsultarUsuario(ReadActivity.this).execute();
            } else {
                Toast.makeText(ReadActivity.this, "Cedula is required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String listarUsuarios() throws IOException, JSONException {
        String url = Constants.URL + "classroom/get-all.php";
        return APIHandler.POSTRESPONSE(url, new ArrayList<>());
    }

    private String consultarUsuario() throws IOException, JSONException {
        String url = Constants.URL + "classroom/get-by-cedula.php";
        List<Map.Entry<String, String>> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry<>("cedula", cedulaQuery.getText().toString().trim()));
        return APIHandler.POSTRESPONSE(url, params);
    }

    class ListarUsuarios extends AsyncTask<String, String, String> {
        private Activity context;
        ListarUsuarios(Activity context) {
            this.context = context;
        }
        protected String doInBackground(String... params) {
            try {
                final String json = listarUsuarios();
                context.runOnUiThread(() -> {
                    try {
                        JSONObject object = new JSONObject(json);
                        JSONArray jsonArray = object.optJSONArray("usuarios");
                        StringBuilder users = new StringBuilder();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject user = jsonArray.getJSONObject(i);
                            users.append(user.getString("username")).append("\n");
                        }
                        userList.setText(users.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class ConsultarUsuario extends AsyncTask<String, String, String> {
        private Activity context;
        ConsultarUsuario(Activity context) {
            this.context = context;
        }
        protected String doInBackground(String... params) {
            try {
                final String json = consultarUsuario();
                context.runOnUiThread(() -> {
                    try {
                        JSONObject object = new JSONObject(json);
                        JSONArray jsonArray = object.optJSONArray("usuarios");
                        if (jsonArray.length() > 0) {
                            JSONObject user = jsonArray.getJSONObject(0);
                            userQueryResult.setText(user.getString("username"));
                        } else {
                            userQueryResult.setText("No user found");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}