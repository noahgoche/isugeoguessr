package com.example.androidexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Chat extends AppCompatActivity {

    private LinearLayout chatMessagesLayout;
    private EditText messageInput;
    private ScrollView chatScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        // Initialize UI components
        chatMessagesLayout = findViewById(R.id.chatMessagesLayout);
        messageInput = findViewById(R.id.messageInput);
        chatScrollView = findViewById(R.id.chatScrollView);
        Button sendButton = findViewById(R.id.sendButton);

        // Placeholder welcome message
        addMessage("Welcome to the chat!", true);

        // Set up Send button to add a new message
        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                addMessage(message, false);  // Add user message
                messageInput.setText("");  // Clear input
            } else {
                Toast.makeText(Chat.this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to add a message to the chat
    private void addMessage(String message, boolean isSystemMessage) {
        TextView messageView = new TextView(this);
        messageView.setText(message);
        messageView.setTextSize(16);
        messageView.setPadding(16, 8, 16, 8);

        if (isSystemMessage) {

        } else {

        }

        chatMessagesLayout.addView(messageView);
        chatScrollView.post(() -> chatScrollView.fullScroll(View.FOCUS_DOWN)); // Auto-scroll to bottom
    }
}
