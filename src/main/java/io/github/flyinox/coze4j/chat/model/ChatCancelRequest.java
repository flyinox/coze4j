package io.github.flyinox.coze4j.chat.model;

import com.google.gson.annotations.SerializedName;

/**
 * Request for canceling an ongoing chat
 */
public class ChatCancelRequest {
    /**
     * The ID of the chat to cancel
     */
    @SerializedName("chat_id")
    private String chatId;

    /**
     * The ID of the conversation
     */
    @SerializedName("conversation_id")
    private String conversationId;

    public static class Builder {
        private final ChatCancelRequest request;

        public Builder(String chatId, String conversationId) {
            request = new ChatCancelRequest();
            request.chatId = chatId;
            request.conversationId = conversationId;
        }

        public ChatCancelRequest build() {
            if (request.chatId == null || request.conversationId == null) {
                throw new IllegalStateException("Chat ID and Conversation ID are required");
            }
            return request;
        }
    }

    // Getters and setters
    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
} 