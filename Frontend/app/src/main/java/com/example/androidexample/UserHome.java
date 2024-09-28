package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserHome extends AppCompatActivity {

    private Button playButton;
    private Button multiplayerButton;
    private Button shopButton;
    private Button leaderboardButton;
    private ImageButton userProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Retrieve username from intent
        String username = getIntent().getStringExtra("USERNAME");

        // Initialize UI elements
        playButton = findViewById(R.id.play_button);
        multiplayerButton = findViewById(R.id.multiplayer_button);
        shopButton = findViewById(R.id.shop_button);
        leaderboardButton = findViewById(R.id.leaderboard_button);
        userProfileButton = findViewById(R.id.user_profile_button);

        // Set welcome text with the username
        //welcomeText.setText("Welcome, " + username + "!");

        // Set click listeners for game buttons
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Play Activity
                //Intent intent = new Intent(UserHome.this, PlayActivity.class);
                //startActivity(intent);
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
}
