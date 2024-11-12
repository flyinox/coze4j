package io.github.flyinox.coze4j.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.github.flyinox.coze4j.chat.model.ChatEventType;
import io.github.flyinox.coze4j.chat.model.ChatObject;
import io.github.flyinox.coze4j.conversation.message.Message;
import com.google.gson.Gson;

import okhttp3.ResponseBody;

/**
 * Handler for streaming chat responses
 */
public class ChatStreamHandler {
    private static final Gson gson = new Gson();

    /**
     * Process streaming response with callbacks
     */
    public static void handleStream(ResponseBody responseBody, ChatStreamCallback callback) 
            throws IOException {
        if (responseBody == null) return;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(responseBody.byteStream()))) {
                    
            String currentEvent = null;
            String line;
            
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                if (line.startsWith("event:")) {
                    currentEvent = line.substring(6).trim();
                    continue;
                }

                if (line.startsWith("data:")) {
                    String data = line.substring(5).trim();
                    
                    // Process event based on current event type
                    if (currentEvent != null) {
                        try {
                            processEvent(currentEvent, data, callback);
                        } catch (Exception e) {
                            callback.onError("Failed to process event: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    private static void processEvent(String eventType, String data, ChatStreamCallback callback) {
        try {
            ChatEventType type = ChatEventType.fromString(eventType);
            
            switch (type) {
                case CHAT_CREATED:
                case CHAT_IN_PROGRESS:
                case CHAT_COMPLETED:
                case CHAT_FAILED:
                case CHAT_REQUIRES_ACTION:
                    ChatObject chatObject = gson.fromJson(data, ChatObject.class);
                    callback.onChatEvent(type, chatObject);
                    break;

                case MESSAGE_DELTA:
                case MESSAGE_COMPLETED:
                    Message message = gson.fromJson(data, Message.class);
                    callback.onMessageEvent(type, message);
                    break;

                case DONE:
                    callback.onComplete();
                    break;

                case ERROR:
                    callback.onError(data);
                    break;
            }
        } catch (Exception e) {
            callback.onError("Error processing event " + eventType + ": " + e.getMessage());
        }
    }

    /**
     * Callback interface for handling stream events
     */
    public interface ChatStreamCallback {
        void onChatEvent(ChatEventType eventType, ChatObject chat);
        void onMessageEvent(ChatEventType eventType, Message message);
        void onError(String errorData);
        void onComplete();
    }

    /**
     * Base implementation of ChatStreamCallback
     */
    public static abstract class BaseChatStreamCallback implements ChatStreamCallback {
        @Override
        public void onComplete() {
            // Default empty implementation
        }
    }
} 