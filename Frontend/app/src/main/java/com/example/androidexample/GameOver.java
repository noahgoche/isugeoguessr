package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GameOver extends AppCompatActivity {

    String username;
    private Button homeButton;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        username = getIntent().getStringExtra("USERNAME");

        // Initialize UI components
        homeButton = findViewById(R.id.homeButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        double gameScore = getIntent().getExtras().getDouble("GAME_SCORE");
        scoreTextView.setText("Your Score: " + gameScore);

        updateStats(username, gameScore);

        // Set up the Home button to open UserHome activity
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameOver.this, UserHome.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            finish();
        });
    }

    private void updateStats(String username, double gameScore) {
        String URL_GET_STATS = "http://coms-3090-070.class.las.iastate.edu:8080/Stats";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_GET_STATS,
                null,
                response -> {
                    try {
                        // Find the stats record for the specified username
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject statsRecord = response.getJSONObject(i);
                            if (statsRecord.getString("username").equals(username)) {
                                int gamesPlayed = statsRecord.getInt("gamesPlayed");
                                int totalScore = statsRecord.getInt("totalScore");

                                int updatedGamesPlayed = gamesPlayed + 1;
                                int updatedTotalScore = totalScore + (int) gameScore;
                                int statsId = statsRecord.getInt("id");

                                // Chain updates: first update the total score, then games played
                                updateTotalScoreAndChain(statsId, updatedTotalScore, updatedGamesPlayed);
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Stats Error", error.toString())
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void updateTotalScoreAndChain(int id, int updatedTotalScore, int updatedGamesPlayed) {
        String url = "http://coms-3090-070.class.las.iastate.edu:8080/Stats/" + id + "/totalScore/" + updatedTotalScore;

        StringRequest putRequest = new StringRequest(
                Request.Method.PUT,
                url,
                response -> {
                    if ("Success".equals(response)) {
                        Log.i("Update TotalScore", "Total score update succeeded");

                        // Chain to update games played after total score update
                        updateGamesPlayed(id, updatedGamesPlayed);
                    } else {
                        Log.e("Update TotalScore", "Unexpected response: " + response);
                    }
                },
                error -> Log.e("Update Error", error.toString())
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(putRequest);
    }

    private void updateGamesPlayed(int id, int updatedGamesPlayed) {
        String url = "http://coms-3090-070.class.las.iastate.edu:8080/Stats/" + id + "/gamesPlayed/" + updatedGamesPlayed;

        StringRequest putRequest = new StringRequest(
                Request.Method.PUT,
                url,
                response -> {
                    if ("Success".equals(response)) {
                        Log.i("Update GamesPlayed", "Games played update succeeded");
                    } else {
                        Log.e("Update GamesPlayed", "Unexpected response: " + response);
                    }
                },
                error -> Log.e("Update Error", error.toString())
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(putRequest);
    }


}
