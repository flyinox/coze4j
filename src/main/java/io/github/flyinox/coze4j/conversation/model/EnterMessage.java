package io.github.flyinox.coze4j.conversation.model;

import java.util.Map;

import io.github.flyinox.coze4j.conversation.message.ContentType;
import io.github.flyinox.coze4j.conversation.message.MessageRole;
import io.github.flyinox.coze4j.conversation.message.MessageType;
import com.google.gson.annotations.SerializedName;

/**
 * Message in conversation
 */
public class EnterMessage {
    private String role;
    private String type;
    private String content;
    
    @SerializedName("content_type")
    private String contentType;
    
    @SerializedName("meta_data")
    private Map<String, String> metaData;

    //getters
    public String getRole() {
        return role;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public static class Builder {
        private final EnterMessage message;

        public Builder(MessageRole role) {
            message = new EnterMessage();
            message.role = role.getValue();
        }

        public Builder type(MessageType type) {
            message.type = type.getValue();
            return this;
        }

        public Builder content(String content) {
            message.content = content;
            return this;
        }

        public Builder contentType(ContentType contentType) {
            message.contentType = contentType.getValue();
            return this;
        }

        public Builder metaData(Map<String, String> metaData) {
            message.metaData = metaData;
            return this;
        }

        public EnterMessage build() {
            return message;
        }
    }

    // Getters and setters
} 