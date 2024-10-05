package com.agomez.crud_classroom;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeleteActivity extends AppCompatActivity {

    private EditText cedula;
    private Button submitDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        cedula = findViewById(R.id.cedula);
        submitDelete = findViewById(R.id.submitDelete);

        submitDelete.setOnClickListener(v -> {
            if (!cedula.getText().toString().trim().isEmpty()) {
                new Eliminar(DeleteActivity.this).execute();
            } else {
                Toast.makeText(DeleteActivity.this, "Cedula is required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean eliminar() {
        String url = Constants.URL + "classroom/delete.php";
        List<Map.Entry<String, String>> params = new ArrayList<>();
        params.add(new AbstractMap.SimpleEntry<>("cedula", cedula.getText().toString().trim()));
        return APIHandler.POST(url, params);
    }

    class Eliminar extends AsyncTask<String, String, String> {
        private Activity context;
        Eliminar(Activity context) {
            this.context = context;
        }
        protected String doInBackground(String... params) {
            if (eliminar())
                context.runOnUiThread(() -> {
                    Toast.makeText(context, "User deleted successfully", Toast.LENGTH_LONG).show();
                    cedula.setText("");
                });
            else
                context.runOnUiThread(() -> Toast.makeText(context, "Failed to delete user", Toast.LENGTH_LONG).show());
            return null;
        }
    }
}