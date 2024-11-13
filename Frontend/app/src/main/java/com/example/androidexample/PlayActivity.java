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



/**
 * PlayActivity that handles the game logic, including displaying panoramic images,
 * interacting with a map to select locations, and calculating scores.
 */
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

    /**
     * Called when the activity is created.
     * Initializes the panoramic image, map, and other UI elements, and starts the first round of the game.
     *
     * @param savedInstanceState the saved state of the activity.
     */
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

        // Initialize other UI elements
        mapView = findViewById(R.id.mapView);
        submitLocationButton = findViewById(R.id.submitLocationButton);
        Button mapToggleButton = findViewById(R.id.mapToggleButton);

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

    /**
     * Returns the correct latitude for the current round.
     *
     * @return the correct latitude for the current round.
     */
    private double getCorrectLatitude() {
        switch (currentRound) {
            case 0: return 42.025227;
            case 1: return 42.025659;
            case 2: return 42.025000;
            case 3: return 42.024500;
            case 4: return 42.024000;
            default: return 0;
        }
    }

    /**
     * Returns the correct longitude for the current round.
     *
     * @return the correct longitude for the current round.
     */
    private double getCorrectLongitude() {
        switch (currentRound) {
            case 0: return -93.649116;
            case 1: return -93.648445;
            case 2: return -93.647000;
            case 3: return -93.646500;
            case 4: return -93.646000;
            default: return 0;
        }
    }

    /**
     * Ends the game and transitions to the GameOver activity with the final score.
     */
    private void endGame() {
        Intent intent = new Intent(PlayActivity.this, GameOver.class);
        intent.putExtra("GAME_SCORE", gameScore);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }

    /**
     * Starts the next round by setting the appropriate image for the round and resetting the map marker.
     */
    private void startRound() {
        if (currentRound < TOTAL_ROUNDS) {
            switch (currentRound) {
                case 0: currentImageResourceId = R.drawable.enviormental; break;
                case 1: currentImageResourceId = R.drawable.carver; break;
                case 2: currentImageResourceId = R.drawable.image3tmp; break;
                case 3: currentImageResourceId = R.drawable.image4tmp; break;
                case 4: currentImageResourceId = R.drawable.sighisoara_sphere; break;
                default: endGame(); return;
            }

            updatePanoramaImage(currentImageResourceId);

            // Clear the current marker from the map for a fresh start
            if (currentMarker != null) {
                mapView.getOverlays().remove(currentMarker);
                currentMarker = null;
            }
        } else {
            endGame(); // End the game if all rounds are completed
        }
    }

    /**
     * Calculates the score based on the distance between the selected and correct locations.
     *
     * @param guessLatitude the latitude of the guessed location.
     * @param guessLongitude the longitude of the guessed location.
     * @param correctLatitude the correct latitude.
     * @param correctLongitude the correct longitude.
     */
    private void calculateScore(double guessLatitude, double guessLongitude, double correctLatitude, double correctLongitude) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(correctLatitude - guessLatitude);
        double dLon = Math.toRadians(correctLongitude - guessLongitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(guessLatitude)) * Math.cos(Math.toRadians(correctLatitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int maxScore = 1000;
        int minScore = 0;
        double maxDistance = 5.0;

        double score = Math.max(minScore, Math.min(maxScore, maxScore * (1 - (distance / maxDistance))));

        gameScore += (int) score;

        Toast.makeText(this, "Score: " + (int) score, Toast.LENGTH_LONG).show();
        System.out.println("Score: " + (int) score);
    }

    /**
     * Updates the panorama image displayed on the screen.
     *
     * @param imageResourceId the resource ID of the image to be displayed.
     */
    private void updatePanoramaImage(int imageResourceId) {
        panorama.setImage(new PLImage(PLUtils.getBitmap(this, imageResourceId), false));
    }

    /**
     * Places a marker on the map at the selected latitude and longitude.
     *
     * @param latitude the latitude of the selected location.
     * @param longitude the longitude of the selected location.
     */
    private void placeMarker(double latitude, double longitude) {
        if (currentMarker != null) {
            mapView.getOverlays().remove(currentMarker);
        }

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
