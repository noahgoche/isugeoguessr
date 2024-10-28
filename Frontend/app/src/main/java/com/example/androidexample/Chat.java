package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.handshake.ServerHandshake;

public class Chat extends AppCompatActivity implements WebSocketListener {

    private Button sendBtn, backMainBtn;
    private EditText msgEtx;
    private TextView msgTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.tx1);

        // Initialize UI elements
        sendBtn = findViewById(R.id.sendBtn);
        backMainBtn = findViewById(R.id.backMainBtn);
        msgEtx = findViewById(R.id.msgEdt);
        msgTv = findViewById(R.id.tx1);

        // Connect this activity to the WebSocket instance
        WebSocketManager1.getInstance().setWebSocketListener(Chat.this);

        // Send button listener
        sendBtn.setOnClickListener(v -> {
            String message = msgEtx.getText().toString();
            if (!message.isEmpty()) {
                try {
                    // Send message through WebSocket
                    WebSocketManager1.getInstance().sendMessage(message);
                    msgEtx.setText(""); // Clear input field
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage());
                    Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button listener
        backMainBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    // Handle incoming WebSocket messages

    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            String currentMessages = msgTv.getText().toString();
            msgTv.setText(currentMessages + "\n" + message);
        });
    }

    // Handle WebSocket close events

    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String currentMessages = msgTv.getText().toString();
            msgTv.setText(currentMessages + "\n---\nConnection closed by " + closedBy + "\nReason: " + reason);
        });
    }

    // Handle WebSocket open events

    public void onWebSocketOpen(ServerHandshake handshakedata) {
        runOnUiThread(() -> {
            String currentMessages = msgTv.getText().toString();
            msgTv.setText(currentMessages + "\n---\nConnected to chat!");
        });
    }

    // Handle WebSocket errors

    public void onWebSocketError(Exception ex) {
        runOnUiThread(() -> {
            String currentMessages = msgTv.getText().toString();
            msgTv.setText(currentMessages + "\n---\nError: " + ex.getMessage());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // WebSocketManager1.getInstance().disconnect(); // Disconnect WebSocket when activity is destroyed
    }
}
