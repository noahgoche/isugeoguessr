package com.example.androidexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);  // Ensure this matches your XML layout file name

        // Set up the Back button to close this activity and return to the previous screen
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Initialize UI elements to display user stats
        TextView hoursPlayedValue = findViewById(R.id.hoursPlayedValue);
        TextView gamesPlayedValue = findViewById(R.id.gamesPlayedValue);
        TextView totalScoreValue = findViewById(R.id.totalScoreValue);
        TextView perfectScoresValue = findViewById(R.id.perfectScoresValue);

        // Example: Set default or placeholder values for stats (replace with actual data as needed)
        hoursPlayedValue.setText("15");       // Placeholder: replace with actual hours played
        gamesPlayedValue.setText("30");       // Placeholder: replace with actual games played
        totalScoreValue.setText("1000");      // Placeholder: replace with total score earned
        perfectScoresValue.setText("5");      // Placeholder: replace with actual perfect scores
    }
}
