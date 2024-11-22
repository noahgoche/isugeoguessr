package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.panoramagl.PLManager;
import com.panoramagl.utils.PLUtils;
import com.panoramagl.PLSphericalPanorama;
import com.panoramagl.PLImage;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlayActivity extends AppCompatActivity {

    private PLManager plManager;
    private PLSphericalPanorama panorama;
    private MapView mapView;
    private Button submitLocationButton;
    private Marker currentMarker;
    private static final int TOTAL_ROUNDS = 5;
    private int currentRound = 0;
    private double gameScore;
    private int currentImageResourceId = R.drawable.sighisoara_sphere; // Default image resource ID
    double latitude;
    double longitude;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        username = getIntent().getStringExtra("USERNAME");

        // Initialize osmdroid configuration
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_play);

        // Initialize PanoramaGL Manager and link to the PLView in XML
        plManager = new PLManager(this);
        plManager.setContentView(findViewById(R.id.locationPhoto));
        plManager.onCreate();

        findViewById(R.id.locationPhoto).setOnTouchListener((v, event) -> plManager.onTouchEvent(event));

        // Set up the spherical panorama
        panorama = new PLSphericalPanorama();
        panorama.getCamera().lookAt(30.0f, 90.0f);
        panorama.getCamera().setYMin(0.5f);
        panorama.getCamera().setYMax(2.0f);
        plManager.setPanorama(panorama);


        // Set the initial image
        //updatePanoramaImage(currentImageResourceId);

        // Initialize other UI elements
        mapView = findViewById(R.id.mapView);
        submitLocationButton = findViewById(R.id.submitLocationButton);
        Button mapToggleButton = findViewById(R.id.mapToggleButton);

        // Start the first round


        // Toggle map visibility when map button is clicked
        mapToggleButton.setOnClickListener(v -> {
            if (mapView.getVisibility() == View.GONE) {
                mapView.setVisibility(View.VISIBLE);
                submitLocationButton.setVisibility(View.VISIBLE);
                mapToggleButton.setText("Close Map");
            } else {
                mapView.setVisibility(View.GONE);
                submitLocationButton.setVisibility(View.GONE);
                mapToggleButton.setText("Map");
            }
        });

        // Set up the map
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(18.0);
        GeoPoint startPoint = new GeoPoint(42.0267, -93.6465);
        mapView.getController().setCenter(startPoint);

        // Custom overlay to detect single taps
        Overlay touchOverlay = new Overlay() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e, MapView mapView) {
                GeoPoint tappedPoint = (GeoPoint) mapView.getProjection().fromPixels((int) e.getX(), (int) e.getY());
                placeMarker(tappedPoint.getLatitude(), tappedPoint.getLongitude());
                return true;
            }
        };
        mapView.getOverlays().add(touchOverlay);
        startRound();

        // Set click listener for submit button to confirm the marker location
        submitLocationButton.setOnClickListener(v -> {
            if (currentMarker != null) {
                latitude = currentMarker.getPosition().getLatitude();
                longitude = currentMarker.getPosition().getLongitude();

                // Calculate score here
                calculateScore(latitude, longitude, getCorrectLatitude(), getCorrectLongitude());

                // Move to the next round
                currentRound++;
                startRound();
            } else {
                Toast.makeText(this, "No location selected!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private double getCorrectLatitude() {
        // Return the correct latitude based on the current round
        switch (currentRound) {
            case 0: return 42.025227; // Correct latitude for round 1
            case 1: return 42.025659; // Correct latitude for round 2
            case 2: return 42.025000; // Add correct latitude for round 3
            case 3: return 42.024500; // Add correct latitude for round 4
            case 4: return 42.024000; // Add correct latitude for round 5
            default: return 0; // Default return
        }
    }
    private double getCorrectLongitude() {
        // Return the correct longitude based on the current round
        switch (currentRound) {
            case 0: return -93.649116; // Correct longitude for round 1
            case 1: return -93.648445; // Correct longitude for round 2
            case 2: return -93.647000; // Add correct longitude for round 3
            case 3: return -93.646500; // Add correct longitude for round 4
            case 4: return -93.646000; // Add correct longitude for round 5
            default: return 0; // Default return
        }
    }
    private void endGame() {
        // send out gameScore
        Intent intent = new Intent(PlayActivity.this, GameOver.class);
        intent.putExtra("GAME_SCORE", gameScore);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }

    private void startRound() {
        if (currentRound < TOTAL_ROUNDS) {
            // Set image for the current round
            switch (currentRound) {
                case 0:
                    currentImageResourceId = R.drawable.enviormental;
                    break;
                case 1:
                    currentImageResourceId = R.drawable.carver;
                    break;
                case 2:
                    currentImageResourceId = R.drawable.image3tmp;
                    break;
                case 3:
                    currentImageResourceId = R.drawable.image4tmp;
                    break;
                case 4:
                    currentImageResourceId = R.drawable.sighisoara_sphere;
                    break;
                default:
                    endGame(); // Ensure endGame is called if rounds exceed
                    return;
            }

            // Update the panorama image immediately
            updatePanoramaImage(currentImageResourceId);

            // Clear the current marker from the map for a fresh start in each round
            if (currentMarker != null) {
                mapView.getOverlays().remove(currentMarker);
                currentMarker = null;
            }
        } else {
            endGame(); // End the game if all rounds are completed
        }
    }
    private void calculateScore(double guessLatitude, double guessLongitude, double correctLatitude, double correctLongitude) {
        // Earth radius in kilometers
        double earthRadius = 6371;
        double dLat = Math.toRadians(correctLatitude - guessLatitude);
        double dLon = Math.toRadians(correctLongitude - guessLongitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(guessLatitude)) * Math.cos(Math.toRadians(correctLatitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c; // Distance in kilometers

        // Calculate score based on distance
        int maxScore = 1000; // Maximum score
        int minScore = 0; // Minimum score
        double maxDistance = 5.0; // Maximum distance in kilometers for scoring purposes (5 miles ~ 8 km)

        // Calculate the score inversely proportional to the distance
        double score = Math.max(minScore, Math.min(maxScore, maxScore * (1 - (distance / maxDistance))));

        // Accumulate score
        gameScore += (int) score; // Store score as an integer

        // Provide feedback
        Toast.makeText(this, "Score: " + (int) score, Toast.LENGTH_LONG).show();
        System.out.println("Score: " + (int) score); // For debugging
    }

    private void updatePanoramaImage(int imageResourceId) {
        // Update the panorama image dynamically
        panorama.setImage(new PLImage(PLUtils.getBitmap(this, imageResourceId), false));
    }

    private void placeMarker(double latitude, double longitude) {
        // Remove previous marker, if any
        if (currentMarker != null) {
            mapView.getOverlays().remove(currentMarker);
        }

        // Add a new marker at the selected location
        currentMarker = new Marker(mapView);
        currentMarker.setPosition(new GeoPoint(latitude, longitude));
        currentMarker.setTitle("Selected Location");
        currentMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(currentMarker);
        mapView.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        plManager.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        plManager.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        plManager.onDestroy();
    }



}
