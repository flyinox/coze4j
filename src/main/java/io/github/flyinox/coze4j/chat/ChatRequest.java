package io.github.flyinox.coze4j.chat;

import java.util.List;
import java.util.Map;

import io.github.flyinox.coze4j.conversation.model.EnterMessage;
import com.google.gson.annotations.SerializedName;

/**
 * Request for chat operations
 */
public class ChatRequest {
    /**
     * The ID of the bot to chat with
     * Required field
     */
    @SerializedName("bot_id")
    private String botId;

    /**
     * The user ID who calls the API
     * Required field
     */
    @SerializedName("user_id")
    private String userId;

    /**
     * Additional messages for the conversation
     * Maximum 100 messages
     */
    @SerializedName("additional_messages")
    private List<EnterMessage> additionalMessages;

    /**
     * Whether to use streaming response
     * Default: false
     */
    private Boolean stream;

    /**
     * Custom variables for prompt templates
     * Supports Jinja2 syntax
     */
    @SerializedName("custom_variables")
    private Map<String, String> customVariables;

    /**
     * Whether to save chat history
     * Default: true
     * Must be true when stream is false
     */
    @SerializedName("auto_save_history")
    private Boolean autoSaveHistory;

    /**
     * Additional metadata
     * Maximum 16 key-value pairs
     * Key length: 1-64 characters
     * Value length: 1-512 characters
     */
    @SerializedName("meta_data")
    private Map<String, String> metaData;

    /**
     * Extra parameters for special scenarios
     * Supported keys: latitude, longitude
     */
    @SerializedName("extra_params")
    private Map<String, String> extraParams;

    public static class Builder {
        private final ChatRequest request;

        public Builder(String botId, String userId) {
            request = new ChatRequest();
            request.botId = botId;
            request.userId = userId;
            request.autoSaveHistory = true; // default value
            request.stream = false; // default value
        }

        public Builder additionalMessages(List<EnterMessage> messages) {
            request.additionalMessages = messages;
            return this;
        }

        public Builder stream(Boolean stream) {
            request.stream = stream;
            return this;
        }

        public Builder customVariables(Map<String, String> variables) {
            request.customVariables = variables;
            return this;
        }

        public Builder autoSaveHistory(Boolean autoSaveHistory) {
            request.autoSaveHistory = autoSaveHistory;
            return this;
        }

        public Builder metaData(Map<String, String> metaData) {
            request.metaData = metaData;
            return this;
        }

        public Builder extraParams(Map<String, String> extraParams) {
            request.extraParams = extraParams;
            return this;
        }

        public ChatRequest build() {
            if (request.botId == null || request.userId == null) {
                throw new IllegalStateException("Bot ID and User ID are required");
            }
            if (!request.autoSaveHistory && !request.stream) {
                throw new IllegalStateException(
                    "autoSaveHistory must be true when stream is false");
            }
            if (request.additionalMessages != null && request.additionalMessages.size() > 100) {
                throw new IllegalStateException(
                    "Maximum 100 additional messages are allowed");
            }
            return request;
        }
    }

    public String getBotId() {
        return botId;
    }

    public String getUserId() {
        return userId;
    }

    public List<EnterMessage> getAdditionalMessages() {
        return additionalMessages;
    }

    public Boolean getStream() {
        return stream;
    }

    public Map<String, String> getCustomVariables() {
        return customVariables;
    }

    public Boolean getAutoSaveHistory() {
        return autoSaveHistory;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public Map<String, String> getExtraParams() {
        return extraParams;
    }
} 