package com.example.androidexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class to see leaderboard of all users and their total score
 */
public class Leaderboard extends AppCompatActivity {

    private LinearLayout leaderboardList;
    private WebSocketClient webSocketClient;
    private int rankCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
        leaderboardList = findViewById(R.id.leaderboardList);
        setupWebSocket();

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }

    private void setupWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://coms-3090-070.class.las.iastate.edu:8080/Leaderboard");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshake) {
                runOnUiThread(() -> {
                    leaderboardList.removeAllViews();
                    rankCounter = 1;
                });
            }

            @Override
            public void onMessage(String message) {
                runOnUiThread(() -> {
                    TextView textView = new TextView(Leaderboard.this);
                    textView.setText(rankCounter + ". " + message);
                    textView.setTextSize(18);
                    textView.setPadding(0, 8, 0, 8);
                    leaderboardList.addView(textView);
                    rankCounter++;
                });
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                runOnUiThread(() -> {
                    TextView textView = new TextView(Leaderboard.this);
                    textView.setText("Connection closed: " + reason);
                    leaderboardList.addView(textView);
                });
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
        webSocketClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}
