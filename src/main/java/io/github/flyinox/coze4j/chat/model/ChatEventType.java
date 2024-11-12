package io.github.flyinox.coze4j.chat.model;

/**
 * Chat streaming event types
 */
public enum ChatEventType {
    CHAT_CREATED("conversation.chat.created"),
    CHAT_IN_PROGRESS("conversation.chat.in_progress"),
    MESSAGE_DELTA("conversation.message.delta"),
    MESSAGE_COMPLETED("conversation.message.completed"),
    CHAT_COMPLETED("conversation.chat.completed"),
    CHAT_FAILED("conversation.chat.failed"),
    CHAT_REQUIRES_ACTION("conversation.chat.requires_action"),
    ERROR("error"),
    DONE("done");

    private final String value;

    ChatEventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ChatEventType fromString(String text) {
        for (ChatEventType type : ChatEventType.values()) {
            if (type.value.equals(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown event type: " + text);
    }
} 