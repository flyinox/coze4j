package io.github.flyinox.coze4j.chat.model;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Chat object containing detailed information about a chat session
 */
public class ChatObject {
    /**
     * The unique identifier of the chat
     */
    private String id;

    /**
     * The ID of the conversation
     */
    @SerializedName("conversation_id")
    private String conversationId;

    /**
     * The ID of the bot for this chat
     */
    @SerializedName("bot_id")
    private String botId;

    /**
     * Creation time (Unix timestamp in seconds)
     */
    @SerializedName("created_at")
    private Integer createdAt;

    /**
     * Completion time (Unix timestamp in seconds)
     */
    @SerializedName("completed_at")
    private Integer completedAt;

    /**
     * Failure time (Unix timestamp in seconds)
     */
    @SerializedName("failed_at")
    private Integer failedAt;

    /**
     * Additional metadata for the chat
     * Key length: 1-64 characters
     * Value length: 1-512 characters
     * Maximum 16 key-value pairs
     */
    @SerializedName("meta_data")
    private Map<String, String> metaData;

    /**
     * Error information when chat fails
     */
    @SerializedName("last_error")
    private LastError lastError;

    /**
     * Chat status:
     * created: Chat created
     * in_progress: Bot is processing
     * completed: Chat completed
     * failed: Chat failed
     * requires_action: Chat needs further action
     * canceled: Chat canceled
     */
    private String status;

    /**
     * Required action details when status is 'requires_action'
     */
    @SerializedName("required_action")
    private RequiredAction requiredAction;

    /**
     * Token usage information
     */
    private TokenUsage usage;

    /**
     * Error information structure
     */
    public static class LastError {
        private Integer code;
        private String msg;
    }

    /**
     * Required action information
     */
    public static class RequiredAction {
        private String type; // enum: submit_tool_outputs

        @SerializedName("submit_tool_outputs")
        private SubmitToolOutputs submitToolOutputs;

        public static class SubmitToolOutputs {
            @SerializedName("tool_calls")
            private List<ToolCall> toolCalls;

            public static class ToolCall {
                private String id;
                private String type; // enum: function
                private Function function;

                public static class Function {
                    private String name;
                    private String arguments;
                }

            }

        }
    }

    /**
     * Token usage information
     */
    public static class TokenUsage {
        @SerializedName("token_count")
        private Integer tokenCount;

        @SerializedName("output_count")
        private Integer outputCount;

        @SerializedName("input_count")
        private Integer inputCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Integer completedAt) {
        this.completedAt = completedAt;
    }

    public Integer getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(Integer failedAt) {
        this.failedAt = failedAt;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public LastError getLastError() {
        return lastError;
    }

    public void setLastError(LastError lastError) {
        this.lastError = lastError;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RequiredAction getRequiredAction() {
        return requiredAction;
    }

    public void setRequiredAction(RequiredAction requiredAction) {
        this.requiredAction = requiredAction;
    }

    public TokenUsage getUsage() {
        return usage;
    }

    public void setUsage(TokenUsage usage) {
        this.usage = usage;
    }

    /**
     * Get chat status as enum
     */
    public ChatStatus getStatusEnum() {
        return ChatStatus.fromString(status);
    }
}
