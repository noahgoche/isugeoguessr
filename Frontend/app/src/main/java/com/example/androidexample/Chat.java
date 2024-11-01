package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.handshake.ServerHandshake;

public class Chat extends AppCompatActivity implements WebSocketListener {

    private Button sendBtn, backButton;
    private EditText msgEtx;
    private TextView msgTv;
    private ScrollView chatScrollView;  // Reference to ScrollView for auto-scrolling

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        // Placeholder for username, ensure it is passed correctly
        String username = getIntent().getStringExtra("USERNAME");
        String serverUrl = "ws://coms-3090-070.class.las.iastate.edu:8080/chat/" + username;  // Use correct server address

        // Establish WebSocket connection and set listener
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(this);

        // Initialize UI elements
        sendBtn = findViewById(R.id.sendBtn);
        msgEtx = findViewById(R.id.msgEdt);
        msgTv = findViewById(R.id.tx1);
        chatScrollView = findViewById(R.id.chatScrollView);  // Assuming ScrollView has this ID

        // Send button listener
        sendBtn.setOnClickListener(v -> {
            String message = msgEtx.getText().toString();
            if (!message.isEmpty()) {
                try {
                    // Send message via WebSocket
                    WebSocketManager.getInstance().sendMessage(message);
                    msgEtx.setText(""); // Clear input field
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage());
                    Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }

        // Method to add a message and scroll to the bottom
    private void addMessageToChat(String message) {
        String currentMessages = msgTv.getText().toString();
        msgTv.setText(currentMessages + "\n" + message);

        // Scroll to the bottom of the chat
        chatScrollView.post(() -> chatScrollView.fullScroll(ScrollView.FOCUS_DOWN));
    }


    // Handle incoming WebSocket messages
    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> addMessageToChat(message));
    }

    // Handle WebSocket close events
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> addMessageToChat("---\nConnection closed by " + closedBy + "\nReason: " + reason));
    }

    // Handle WebSocket open events
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        runOnUiThread(() -> addMessageToChat("---\nConnected to chat!"));
    }

    // Handle WebSocket errors
    @Override
    public void onWebSocketError(Exception ex) {
        runOnUiThread(() -> addMessageToChat("---\nError: " + ex.getMessage()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebSocketManager.getInstance().disconnect(); // Disconnect WebSocket when activity is destroyed
    }
}
