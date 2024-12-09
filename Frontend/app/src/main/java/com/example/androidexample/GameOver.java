package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * GameOver activity that displays the final score after the game ends and updates the user's game statistics on the server.
 */
public class GameOver extends AppCompatActivity {

    String username;
    private int perfectGuesses;
    private Button homeButton;
    private TextView scoreTextView;

    /**
     * Called when the activity is created. Initializes UI components and sets up the logic for displaying the score.
     * Also updates the user's game statistics by sending requests to the server.
     *
     * @param savedInstanceState the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        username = getIntent().getStringExtra("USERNAME");
        perfectGuesses = getIntent().getIntExtra("PERFECT_GUESSES", 0);
        // Initialize UI components
        homeButton = findViewById(R.id.homeButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        // Retrieve and display game score
        double gameScore = getIntent().getExtras().getDouble("GAME_SCORE");
        scoreTextView.setText("Your Score: " + gameScore);

        // Update user statistics with the game score
        updateStats(username, gameScore);

        // Set up the Home button to open UserHome activity
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameOver.this, UserHome.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Updates the user's game statistics on the server by retrieving the current stats and updating the total score and games played.
     *
     * @param username the username of the user whose statistics need to be updated.
     * @param gameScore the score achieved by the user in the game.
     */
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
                                int perfectGames = statsRecord.getInt("perfectGames");
                                int currentPerfectGuesses = statsRecord.getInt("perfectGuesses");

                                int updatedGamesPlayed = gamesPlayed + 1;
                                int updatedTotalScore = totalScore + (int) gameScore;
                                int statsId = statsRecord.getInt("id");

                                // Chain updates: first update the total score, then games played
                                updateTotalScoreAndChain(statsId, updatedTotalScore, updatedGamesPlayed);

                                if (gameScore == 5000) {
                                    updatePerfectGames(statsId, perfectGames);
                                }
                                if (perfectGuesses != 0) {
                                    updatedPerfectGuesses(statsId, currentPerfectGuesses, perfectGuesses);
                                }
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

    /**
     * Updates the total score for the user in the stats record.
     * After successfully updating the total score, the number of games played is updated.
     *
     * @param id the ID of the user's stats record.
     * @param updatedTotalScore the new total score to be set.
     * @param updatedGamesPlayed the new number of games played to be set.
     */
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

    private void updatePerfectGames(int id, int currentPerfectGames) {
        String url = "http://coms-3090-070.class.las.iastate.edu:8080/Stats/" + id + "/perfectGames/" + (currentPerfectGames+1);

        StringRequest putRequest = new StringRequest(
                Request.Method.PUT,
                url,
                response -> {
                    if ("Success".equals(response)) {
                        Log.i("Update perfectGames", "Perfect games update succeeded");
                    } else {
                        Log.e("Update perfectGames", "Unexpected response: " + response);
                    }
                },
                error -> Log.e("Update Error", error.toString())
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(putRequest);
    }

    private void updatedPerfectGuesses(int id, int currentPerfectGuesses, int newPerfectGuesses) {
        String url = "http://coms-3090-070.class.las.iastate.edu:8080/Stats/" + id + "/perfectGuesses/" + (currentPerfectGuesses+newPerfectGuesses);
        StringRequest putRequest = new StringRequest(
                Request.Method.PUT,
                url,
                response -> {
                    if ("Success".equals(response)) {
                        Log.i("Update perfect guesses", "Perfect guesses update succeeded");
                    } else {
                        Log.e("Update perfect guesses", "Unexpected response: " + response);
                    }
                },
                error -> Log.e("Update Error", error.toString())
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(putRequest);
    }

    /**
     * Updates the number of games played for the user in the stats record.
     *
     * @param id the ID of the user's stats record.
     * @param updatedGamesPlayed the new number of games played to be set.
     */
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
