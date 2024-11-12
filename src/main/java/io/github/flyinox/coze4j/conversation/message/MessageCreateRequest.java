package io.github.flyinox.coze4j.conversation.message;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Request for creating a message
 */
public class MessageCreateRequest {
    private String role;
    private String content;
    
    @SerializedName("content_type")
    private String contentType;
    
    @SerializedName("meta_data")
    private Map<String, String> metaData;

    //getters
    public String getRole() {
        return role;
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
        private final MessageCreateRequest request;

        public Builder(String role, String content, String contentType) {
            request = new MessageCreateRequest();
            request.role = role;
            request.content = content;
            request.contentType = contentType;
        }

        public Builder content(String content) {
            request.content = content;
            return this;
        }

        public Builder contentType(String contentType) {
            request.contentType = contentType;
            return this;
        }

        public Builder metaData(Map<String, String> metaData) {
            request.metaData = metaData;
            return this;
        }

        public MessageCreateRequest build() {
            return request;
        }
    }
} 