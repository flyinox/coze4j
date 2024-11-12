package io.github.flyinox.coze4j.conversation.message;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * Message information
 */
public class Message {
    private String id;
    
    @SerializedName("conversation_id")
    private String conversationId;
    
    @SerializedName("bot_id")
    private String botId;
    
    @SerializedName("chat_id")
    private String chatId;
    
    @SerializedName("meta_data")
    private Map<String, String> metaData;
    
    private String role;
    private String content;
    
    @SerializedName("content_type")
    private String contentType;
    
    @SerializedName("created_at")
    private Integer createdAt;
    
    @SerializedName("updated_at")
    private Integer updatedAt;
    
    private String type;

    // Getters and setters for all fields
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

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
} 