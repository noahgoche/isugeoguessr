package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private ImageView catImageView;
    private boolean isImageVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView mainMsgTextView = findViewById(R.id.main_msg_txt);
        final Button toggleButton;

        mainMsgTextView.setText(R.string.title);

        toggleButton = findViewById(R.id.toggleButton);
        catImageView = findViewById(R.id.catImageView);

        toggleButton.setOnClickListener(v -> {

            if (isImageVisible) {
                catImageView.setVisibility(View.GONE);
            } else {
                catImageView.setVisibility(View.VISIBLE);
            }
            isImageVisible = !isImageVisible;
        });
    }
}
