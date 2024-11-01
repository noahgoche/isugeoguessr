package com.example.androidexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        TextView hoursPlayedValue = findViewById(R.id.hoursPlayedValue);
        TextView gamesPlayedValue = findViewById(R.id.gamesPlayedValue);
        TextView totalScoreValue = findViewById(R.id.totalScoreValue);
        TextView perfectScoresValue = findViewById(R.id.perfectScoresValue);

        hoursPlayedValue.setText("15");
        gamesPlayedValue.setText("30");
        totalScoreValue.setText("1000");
        perfectScoresValue.setText("5");
    }
}
