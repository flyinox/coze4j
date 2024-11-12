package io.github.flyinox.coze4j.conversation;

import java.util.Map;

import io.github.flyinox.coze4j.CozeBaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Response for conversation creation
 */
public class ConversationResponse extends CozeBaseResponse<ConversationResponse.Data> {

    public static class Data {
        private String id;

        @SerializedName("created_at")
        private Long createdAt;

        @SerializedName("meta_data")
        private Map<String, String> metaData;

        public String getId() {
            return id;
        }

        public Long getCreatedAt() {
            return createdAt;
        }

        public Map<String, String> getMetaData() {
            return metaData;
        }
    }
}