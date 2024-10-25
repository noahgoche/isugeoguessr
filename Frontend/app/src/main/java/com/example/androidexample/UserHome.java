package com.example.androidexample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserHome extends AppCompatActivity {

    private Button playButton;
    private Button multiplayerButton;
    private Button shopButton;
    private Button leaderboardButton;
    private ImageButton userProfileButton;

    private TextView welcomeText;

    private TextView gamesPlayed;
    private TextView totalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve username from intent
        String username = getIntent().getStringExtra("USERNAME");
        String id = getIntent().getStringExtra("ID");

        // Initialize UI elements
        playButton = findViewById(R.id.play_button);
        multiplayerButton = findViewById(R.id.multiplayer_button);
        shopButton = findViewById(R.id.shop_button);
        leaderboardButton = findViewById(R.id.leaderboard_button);
        userProfileButton = findViewById(R.id.user_profile_button);

        // Set welcome text with the username
        welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome, " + username + "!");

        gamesPlayed = findViewById(R.id.gamesPlayed);
        totalScore = findViewById(R.id.totalScore);

        // Set click listeners for game buttons
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Play Activity
                //Intent intent = new Intent(UserHome.this, PlayActivity.class);
                //startActivity(intent);
                gamesPlayed.setText("Games Played: " + 1);
                totalScore.setText("Total Score: " + 500);
                updateStats(Integer.parseInt(id));
            }
        });

        multiplayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Multiplayer Activity
                //Intent intent = new Intent(UserHome.this, MultiplayerActivity.class);
                //startActivity(intent);
            }
        });

        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Shop Activity
                //Intent intent = new Intent(UserHome.this, ShopActivity.class);
                //startActivity(intent);
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Leaderboard Activity
                // Intent intent = new Intent(UserHome.this, LeaderboardActivity.class);
                //startActivity(intent);
            }
        });

        // Set click listener for the user profile button
        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch User Profile Activity
                //Intent intent = new Intent(UserHome.this, UserProfileActivity.class);
                //startActivity(intent);
            }
        });
    }

    private void updateStats(int id) {
        // URL for the PUT request
        String URL_UPDATE_STATS = "http://coms-3090-070.class.las.iastate.edu:8080/Stats";

        // Create JSON object to be sent in the PUT request
        JSONObject statsData = new JSONObject();
        try {
            statsData.put("gamesPlayed", 1);
            statsData.put("totalScore", 500);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create the PUT request
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                URL_UPDATE_STATS,
                statsData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString());
                        try {
                            String result = response.getString("result");
                            if (result.equals("Success")) {
                                Log.d("Update Stats", "Stats updated successfully");
                            } else {
                                Log.d("Update Stats", "Failed to update stats");
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

        // Initialize the request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }

}
