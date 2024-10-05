package com.agomez.crud_classroom;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class APIHandler {

    public static boolean POST(String urlString, List<Map.Entry<String, String>> params) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }

            // Log the request data
            Log.d("APIHandler", "POST Data: " + postData.toString());

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = postData.toString().getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            Log.d("APIHandler", "Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                Log.d("APIHandler", "Response: " + response.toString());
                return true;
            } else {
                Log.e("APIHandler", "Error Response Code: " + responseCode);
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorResponse.append(line);
                }
                Log.e("APIHandler", "Error Response: " + errorResponse.toString());
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("APIHandler", "IOException: " + e.getMessage());
        }
        return false;
}

    public static String POSTRESPONSE(String urlString, List<Map.Entry<String, String>> params) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }

            // Log the request data
            Log.d("APIHandler", "POST Data: " + postData.toString());

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = postData.toString().getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Log the response
            Log.d("APIHandler", "Response: " + response.toString());

            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GET(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            Log.d("APIHandler", "Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                Log.d("APIHandler", "Response: " + response.toString());
                return response.toString();
            } else {
                Log.e("APIHandler", "Error Response Code: " + responseCode);
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorResponse.append(line);
                }
                Log.e("APIHandler", "Error Response: " + errorResponse.toString());
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("APIHandler", "IOException: " + e.getMessage());
        }
        return null;
    }
}