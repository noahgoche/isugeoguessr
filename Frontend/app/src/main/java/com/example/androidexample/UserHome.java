package com.example.androidexample;

import android.content.Intent;
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

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserHome extends AppCompatActivity implements WebSocketListener {

    private Button playButton;
    private Button chatButton;
    private Button statsButton;
    private Button leaderboardButton;
    private Button editProfileButton;

    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve username from intent
        String username = getIntent().getStringExtra("USERNAME");
        String id = getIntent().getStringExtra("ID");

        // Initialize UI elements
        playButton = findViewById(R.id.play_button);
        chatButton = findViewById(R.id.chat_button);
        statsButton = findViewById(R.id.stats_button);
        leaderboardButton = findViewById(R.id.leaderboard_button);
        editProfileButton = findViewById(R.id.edit_profile_button);

        // Set welcome text with the username
        welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome, " + username + "!");


        // Set click listeners for game buttons
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Play Activity
                Intent intent = new Intent(UserHome.this, PlayActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
                finish();
            }
        });

        chatButton.setOnClickListener(view -> {
            //go to chat activity
            Intent intent = new Intent(this, Chat.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        });
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Stats Activity
                Intent intent = new Intent(UserHome.this, Stats.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Leaderboard Activity
                Intent intent = new Intent(UserHome.this, Leaderboard.class);
                startActivity(intent);

            }
        });

        // Set click listener for the user profile button
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch User Profile Activity
                Intent intent = new Intent(UserHome.this, UserProfileActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {

    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}
