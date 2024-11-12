package io.github.flyinox.coze4j.conversation.message;

import com.google.gson.annotations.SerializedName;

/**
 * Request for listing messages in a conversation
 */
public class MessageListRequest {
    /**
     * Sorting order for messages
     * desc: (Default) Sorted in descending order by creation time
     * asc: Sorted in ascending order by creation time
     */
    private String order;

    /**
     * The ID of the Chat
     */
    @SerializedName("chat_id")
    private String chatId;

    /**
     * Get messages before this ID
     */
    @SerializedName("before_id")
    private String beforeId;

    /**
     * Get messages after this ID
     */
    @SerializedName("after_id")
    private String afterId;

    /**
     * Number of messages to return (1-50, default 50)
     */
    private Integer limit;

    public static class Builder {
        private final MessageListRequest request;

        public Builder() {
            request = new MessageListRequest();
        }

        public Builder order(String order) {
            request.order = order;
            return this;
        }

        public Builder chatId(String chatId) {
            request.chatId = chatId;
            return this;
        }

        public Builder beforeId(String beforeId) {
            request.beforeId = beforeId;
            return this;
        }

        public Builder afterId(String afterId) {
            request.afterId = afterId;
            return this;
        }

        public Builder limit(Integer limit) {
            request.limit = limit;
            return this;
        }

        public MessageListRequest build() {
            return request;
        }
    }

    // Getters
    public String getOrder() {  
        return order;
    }

    public String getChatId() {
        return chatId;
    }

    public String getBeforeId() {
        return beforeId;
    }

    public String getAfterId() {
        return afterId;
    }

    public Integer getLimit() {
        return limit;
    }
} 