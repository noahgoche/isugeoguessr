package com.example.androidexample;

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

public class PlayActivity extends AppCompatActivity {

    private PLManager plManager;  // PanoramaGL Manager
    private MapView mapView;
    private Button submitLocationButton;
    private Marker currentMarker;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize osmdroid configuration
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_play);

        // Initialize PanoramaGL Manager and link to the PLView in XML

            plManager = new PLManager(this);
        plManager.setContentView(findViewById(R.id.locationPhoto));  // Link to the PLView defined in XML
        plManager.onCreate();
        plManager.getPanorama().getCamera().setFovFactor(1.0f); // Set a default FOV if needed

        findViewById(R.id.locationPhoto).setOnTouchListener((v, event) -> {
            return plManager.onTouchEvent(event); // Pass the touch event to PLManager
        });
        // Set up the spherical panorama
        PLSphericalPanorama panorama = new PLSphericalPanorama();
        panorama.getCamera().lookAt(30.0f, 90.0f);  // Optional: set initial camera orientation

        // Load the image from raw resources and set it as the panorama image
        panorama.setImage(new PLImage(PLUtils.getBitmap(this, R.drawable.sighisoara_sphere), false));
        plManager.setPanorama(panorama); // Set the panorama

        // Set up the camera to support interactive movement
        panorama.getCamera().setYMin(0.5f);  // Minimum zoom level for more detail
        panorama.getCamera().setYMax(2.0f);  // Maximum zoom level for close-up

        // Enable movement by default (should be interactive by default in PanoramaGL)
        plManager.setAccelerometerEnabled(false);  // Optional: Disable accelerometer if not needed

        // Initialize other UI elements
        mapView = findViewById(R.id.mapView);
        submitLocationButton = findViewById(R.id.submitLocationButton);





        // Set up the map
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(18.0);
        GeoPoint startPoint = new GeoPoint(42.0267, -93.6465);  // Iowa State University
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
                double latitude = currentMarker.getPosition().getLatitude();
                double longitude = currentMarker.getPosition().getLongitude();
                Toast.makeText(this, "Submitted Location:\nLat: " + latitude + "\nLng: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No location selected!", Toast.LENGTH_SHORT).show();
            }
        });
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
        currentMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM); // Set anchor to bottom center for better appearance
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
