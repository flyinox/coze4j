package io.github.flyinox.coze4j.conversation.message;

/**
 * Content type enumeration
 * Defines the type of message content
 */
public enum ContentType {
    TEXT("text"),
    OBJECT_STRING("object_string"),
    CARD("card");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Check if the content type is valid for the given message type
     *
     * @param messageType The type of message
     * @return true if the content type is valid for the message type
     */
    public boolean isValidFor(MessageType messageType) {
        switch (this) {
            case TEXT:
                return true; // Text is valid for all message types
            case OBJECT_STRING:
                return messageType == MessageType.QUESTION; // Only valid for questions
            case CARD:
                return false; // Card is only valid in responses
            default:
                return false;
        }
    }
} 