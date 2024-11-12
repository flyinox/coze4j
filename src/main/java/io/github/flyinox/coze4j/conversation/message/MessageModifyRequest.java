package io.github.flyinox.coze4j.conversation.message;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Request for modifying a message
 */
public class MessageModifyRequest {
    /**
     * Additional information for the message
     * Key length: 1-64 characters
     * Value length: 1-512 characters
     * Maximum 16 key-value pairs
     */
    @SerializedName("meta_data")
    private Map<String, String> metaData;

    /**
     * The content of the message
     * Supports text, multimodal content (text + files/images)
     */
    private String content;

    /**
     * The type of message content
     * text: Text content
     * object_string: Multimodal content
     */
    @SerializedName("content_type")
    private String contentType;

    public static class Builder {
        private final MessageModifyRequest request;

        public Builder() {
            request = new MessageModifyRequest();
        }

        public Builder metaData(Map<String, String> metaData) {
            request.metaData = metaData;
            return this;
        }

        public Builder content(String content) {
            request.content = content;
            return this;
        }

        public Builder contentType(String contentType) {
            request.contentType = contentType;
            return this;
        }

        public MessageModifyRequest build() {
            if (request.content == null && request.metaData == null) {
                throw new IllegalStateException("Either content or metaData must be provided");
            }
            if (request.content != null && request.contentType == null) {
                throw new IllegalStateException("contentType must be provided when content is set");
            }
            return request;
        }
    }

    // Getters and setters
} 