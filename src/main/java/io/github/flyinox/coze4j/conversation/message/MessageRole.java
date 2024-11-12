package io.github.flyinox.coze4j.conversation.message;

/**
 * Message role enumeration
 * Defines who sent the message
 */
public enum MessageRole {
    /**
     * Message sent by user
     */
    USER("user"),
    
    /**
     * Message sent by assistant
     */
    ASSISTANT("assistant");

    private final String value;

    MessageRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
} 