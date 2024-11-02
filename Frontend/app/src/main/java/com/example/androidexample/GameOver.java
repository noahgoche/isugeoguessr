package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    private Button homeButton;
    private TextView scoreTextView;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        username = getIntent().getStringExtra("USERNAME");

        // Initialize UI components
        homeButton = findViewById(R.id.homeButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        // Set the user's score (Placeholder score here)
        int userScore = 75; // Placeholder score
        double gameScore = getIntent().getExtras().getDouble("GAME_SCORE");
        scoreTextView.setText("Your Score: " + gameScore);

        // Set up the Home button to open UserHome activity
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameOver.this, UserHome.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            finish(); // Close GameOver activity to prevent stacking
        });
    }

    // Method to populate leaderboard (placeholder)
    private void populateLeaderboard() {
        // Placeholder leaderboard data
        String[] leaderboardEntries = {
                "1. Player1 - 100",
                "2. Player2 - 90",
                "3. Player3 - 80",
                "4. Player4 - 70",
                "5. Player5 - 60"
        };

        // Display leaderboard entries (commented since LinearLayout was commented)
        for (String entry : leaderboardEntries) {
            TextView textView = new TextView(this);
            textView.setText(entry);
            textView.setTextSize(18);
            textView.setPadding(0, 8, 0, 8);
            //leaderboardLayout.addView(textView); // Uncomment if leaderboardLayout is used
        }
    }
}
