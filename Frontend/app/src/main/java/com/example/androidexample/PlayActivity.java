package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        updatePanoramaImage(currentImageResourceId);

        // Initialize other UI elements
        mapView = findViewById(R.id.mapView);
        submitLocationButton = findViewById(R.id.submitLocationButton);
        Button mapToggleButton = findViewById(R.id.mapToggleButton);

        // Start the first round
        startRound();

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

        // Set click listener for submit button to confirm the marker location
        submitLocationButton.setOnClickListener(v -> {
            if (currentMarker != null) {
                 latitude = currentMarker.getPosition().getLatitude();
                 longitude = currentMarker.getPosition().getLongitude();
                Toast.makeText(this, "Submitted Location:\nLat: " + latitude + "\nLng: " + longitude, Toast.LENGTH_LONG).show();

                // Move to the next round
                currentRound++;

                startRound();
            } else {
                Toast.makeText(this, "No location selected!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void endGame() {
        Intent intent = new Intent(PlayActivity.this, GameOver.class);
        startActivity(intent);
        finish();
    }

    private void startRound() {
        if (currentRound < TOTAL_ROUNDS) {
            // Update the current image resource ID dynamically based on round
            //terible ass jit coding but i couldnt care less
            if (currentRound == 1) {
                calculateScore(latitude,longitude,latitude,longitude);

                //set image for round 1
                currentImageResourceId = R.drawable.room;
                //get random image and the lat and long


            } else if (currentRound == 2) {
                currentImageResourceId = R.drawable.sighisoara_sphere;
            }else if (currentRound == 3) {
                currentImageResourceId = R.drawable.sighisoara_sphere;
            }else if (currentRound == 4) {
                currentImageResourceId = R.drawable.sighisoara_sphere;
            }else if (currentRound == 5) {
                currentImageResourceId = R.drawable.sighisoara_sphere;
            }

            // Apply the panorama image change immediately
            updatePanoramaImage(currentImageResourceId);

            // Clear the current marker from the map for a fresh start in each round
            mapView.getOverlays().remove(currentMarker);
            currentMarker = null;
        } else {
            endGame();
        }
    }
    private void calculateScore(double guessLatitude, double guessLongitude, double correctLatitude, double correctLongitude ){
// some dumb ass math that apparently works
        double earthRadius = 6371; // Radius of the Earth in kilometers
        double dLat = Math.toRadians(correctLatitude - guessLatitude);
        double dLon = Math.toRadians(correctLongitude - guessLongitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(guessLatitude)) * Math.cos(Math.toRadians(correctLatitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c; // Distance in kilometers

        // Calculate score based on distance
        int maxScore = 1000;
        int minScore = 1;
        double maxDistance = 10000; // Maximum distance (in km) for the lowest score
        double score = maxScore - (distance / maxDistance) * (maxScore - minScore);

        // Ensure score is within bounds
        score = Math.max(minScore, Math.min(score, maxScore));
        gameScore += score; // Accumulate score for the entire game
        System.out.println("Score: " + (int) score); // Print or return score
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
