package com.example.androidexample;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class to view users stats
 * Class to view poop stats
 */
public class Stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);

        String username = getIntent().getStringExtra("USERNAME");

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        loadStats(username);
    }

    /**
     * Loads the users stats from the DB based on their username
     * @param username
     */
    private void loadStats(String username) {
        String URL_STATS = "http://coms-3090-070.class.las.iastate.edu:8080/Stats";

        JsonArrayRequest jsonStatsRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_STATS,
                null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject statsRecord = response.getJSONObject(i);

                            // Check if this record's username matches the given username
                            if (statsRecord.getString("username").equals(username)) {
                                int gamesPlayed = statsRecord.getInt("gamesPlayed");
                                int totalScore = statsRecord.getInt("totalScore");

                                // Update UI with the retrieved values
                                TextView gamesPlayedValue = findViewById(R.id.gamesPlayedValue);
                                TextView totalScoreValue = findViewById(R.id.totalScoreValue);

                                gamesPlayedValue.setText(String.valueOf(gamesPlayed));
                                totalScoreValue.setText(String.valueOf(totalScore));
                                break; // Exit loop once the matching record is found
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error
                    Log.e("Stats Error", error.toString());
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonStatsRequest);
    }

}
