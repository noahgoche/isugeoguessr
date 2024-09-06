package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;   // Define message TextView variable
    private Button toggleButton;    // Define Button variable
    private ImageView catImageView; // Define ImageView variable
    private boolean isImageVisible = false; // To track visibility state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Link to Main activity XML

        // Initialize UI elements
        messageText = findViewById(R.id.main_msg_txt);  // Link to message TextView in the Main activity XML
        messageText.setText("This is a test application");

        // Initialize Button and ImageView
        toggleButton = findViewById(R.id.toggleButton);  // Link to Button in XML
        catImageView = findViewById(R.id.catImageView);  // Link to ImageView in XML

        // Set up a click listener for the button
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle image visibility
                if (isImageVisible) {
                    catImageView.setVisibility(View.GONE);  // Hide the image
                } else {
                    catImageView.setVisibility(View.VISIBLE);  // Show the image
                }
                // Update visibility state
                isImageVisible = !isImageVisible;
            }
        });
    }
}
