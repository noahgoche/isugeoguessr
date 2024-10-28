package com.example.androidexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Leaderboard extends AppCompatActivity {

    private LinearLayout leaderboardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);

        // Initialize the leaderboard list layout
        leaderboardList = findViewById(R.id.leaderboardList);

        // Populate the leaderboard with placeholder data
        populateLeaderboard();

        // Set up the Back button to close this activity and return to the previous screen
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }

    // Method to populate the leaderboard with placeholder data
    private void populateLeaderboard() {
        // Placeholder leaderboard entries
        String[] leaderboardEntries = {
                "1. Player1 - 100",
                "2. Player2 - 90",
                "3. Player3 - 80",
                "4. Player4 - 70",
                "5. Player5 - 60"
        };

        // Add each entry as a TextView in the leaderboard list
        for (String entry : leaderboardEntries) {
            TextView textView = new TextView(this);
            textView.setText(entry);
            textView.setTextSize(18);
            textView.setPadding(0, 8, 0, 8);
            leaderboardList.addView(textView);
        }
    }
}
