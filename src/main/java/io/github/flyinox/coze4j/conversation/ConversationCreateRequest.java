package io.github.flyinox.coze4j.conversation;

import java.util.List;
import java.util.Map;

import io.github.flyinox.coze4j.conversation.model.EnterMessage;
import com.google.gson.annotations.SerializedName;

/**
 * Request for creating a conversation
 */
public class ConversationCreateRequest {
    private List<EnterMessage> messages;
    
    @SerializedName("meta_data")
    private Map<String, String> metaData;

    //getters
    public List<EnterMessage> getMessages() {
        return messages;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public static class Builder {
        private final ConversationCreateRequest request;

        public Builder() {
            request = new ConversationCreateRequest();
        }

        public Builder messages(List<EnterMessage> messages) {
            request.messages = messages;
            return this;
        }

        public Builder metaData(Map<String, String> metaData) {
            request.metaData = metaData;
            return this;
        }

        public ConversationCreateRequest build() {
            return request;
        }
    }
} 