//Pipeline test 2

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

    private Button sendBtn, backButton, chatRoom1Button, chatRoom2Button, chatRoom3Button;
    private EditText msgEtx;
    private TextView msgTv;
    private ScrollView chatScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        String username = getIntent().getStringExtra("USERNAME");
        String serverUrl = "ws://coms-3090-070.class.las.iastate.edu:8080/chat/" + username;

        WebSocketManager.getInstance().disconnectWebSocket();

        WebSocketManager.getInstance().connectWebSocket(serverUrl);
        WebSocketManager.getInstance().setWebSocketListener(this);

        sendBtn = findViewById(R.id.sendBtn);
        msgEtx = findViewById(R.id.msgEdt);
        msgTv = findViewById(R.id.tx1);
        chatScrollView = findViewById(R.id.chatScrollView);
        chatRoom1Button = findViewById(R.id.chatRoom1Button);
        chatRoom2Button = findViewById(R.id.chatRoom2Button);
        chatRoom3Button = findViewById(R.id.chatRoom3Button);

        sendBtn.setOnClickListener(v -> {
            String message = msgEtx.getText().toString();
            if (!message.isEmpty()) {
                try {
                    WebSocketManager.getInstance().sendMessage(message);
                    msgEtx.setText("");
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage());
                    Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        chatRoom1Button.setOnClickListener(v -> {
            Intent intent = new Intent(Chat.this, ChatRoom1.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        });
        chatRoom2Button.setOnClickListener(v -> {
            Intent intent = new Intent(Chat.this, ChatRoom1.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        });
        chatRoom3Button.setOnClickListener(v -> {
            Intent intent = new Intent(Chat.this, ChatRoom1.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        });
    }

    private void addMessageToChat(String message) {
        String currentMessages = msgTv.getText().toString();
        msgTv.setText(currentMessages + "\n" + message);
        chatScrollView.post(() -> chatScrollView.fullScroll(ScrollView.FOCUS_DOWN));
    }

    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> addMessageToChat(message));
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> addMessageToChat("---\nConnection closed by " + closedBy + "\nReason: " + reason));
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        runOnUiThread(() -> addMessageToChat("---\nConnected to chat!"));
    }

    @Override
    public void onWebSocketError(Exception ex) {
        runOnUiThread(() -> addMessageToChat("---\nError: " + ex.getMessage()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebSocketManager.getInstance().disconnect();
    }
}
