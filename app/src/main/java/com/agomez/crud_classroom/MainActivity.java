package com.agomez.crud_classroom;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView usersListView;
    private TextView registeredUsersMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        usersListView = findViewById(R.id.usersListView);
        registeredUsersMessage = findViewById(R.id.registeredUsersMessage);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        new FetchStudentsTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchStudentsTask().execute();
    }

    private class FetchStudentsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String url = Constants.URL + "classroom/get-students.php";
            return APIHandler.GET(url);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray usersArray = jsonObject.optJSONArray("usuarios");
                    if (usersArray != null && usersArray.length() > 0) {
                        List<String> usersList = new ArrayList<>();
                        for (int i = 0; i < usersArray.length(); i++) {
                            JSONObject userObject = usersArray.getJSONObject(i);
                            usersList.add(userObject.getString("username"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, usersList);
                        usersListView.setAdapter(adapter);
                    } else {
                        Toast.makeText(MainActivity.this, "No registered users found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MainActivity.this, "Error connecting to server", Toast.LENGTH_LONG).show();
            }
        }
    }
}