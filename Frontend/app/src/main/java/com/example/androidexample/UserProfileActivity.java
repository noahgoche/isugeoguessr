package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {

    private Button deleteButton, backButton;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = getIntent().getStringExtra("USERNAME");
        deleteButton = findViewById(R.id.deleteProfileButton);
        backButton = findViewById(R.id.backButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchUserId(username);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() { // Set up back button logic
            @Override
            public void onClick(View v) {
                finish(); // Close this activity and go back to the previous one
            }
        });
    }

    private void fetchUserId(String username) {
        String URL_GET_USERS = "http://coms-3090-070.class.las.iastate.edu:8080/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_GET_USERS,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String userId = null;
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject userObject = response.getJSONObject(i);
                                if (userObject.getString("username").equals(username)) {
                                    userId = userObject.getString("id");
                                    break;
                                }
                            }

                            if (userId != null) {
                                fetchStatsId(username, userId);
                            } else {
                                Toast.makeText(UserProfileActivity.this, "User not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void fetchStatsId(String username, String userId) {
        String URL_GET_STATS = "http://coms-3090-070.class.las.iastate.edu:8080/Stats";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_GET_STATS,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String statsId = null;
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject statsObject = response.getJSONObject(i);
                                if (statsObject.getString("username").equals(username)) {
                                    statsId = statsObject.getString("id");
                                    break;
                                }
                            }

                            if (statsId != null) {
                                deleteUser(userId, statsId);
                            } else {
                                Toast.makeText(UserProfileActivity.this, "Stats not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void deleteUser(String userId, String statsId) {
        String URL_DELETE_USER = "http://coms-3090-070.class.las.iastate.edu:8080/users/" + userId;

        StringRequest deleteUserRequest = new StringRequest(
                Request.Method.DELETE,
                URL_DELETE_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Delete User", "User deleted successfully: " + response);
                        deleteStats(statsId);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Delete User Error", error.toString());
                        Toast.makeText(UserProfileActivity.this, "Failed to delete user", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(deleteUserRequest);
    }

    private void deleteStats(String statsId) {
        String URL_DELETE_STATS = "http://coms-3090-070.class.las.iastate.edu:8080/Stats/" + statsId;

        StringRequest deleteStatsRequest = new StringRequest(
                Request.Method.DELETE,
                URL_DELETE_STATS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Delete Stats", "Stats deleted successfully: " + response);
                        Toast.makeText(UserProfileActivity.this, "User and stats deleted successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Delete Stats Error", error.toString());
                        Toast.makeText(UserProfileActivity.this, "Failed to delete stats", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(deleteStatsRequest);
    }
}
