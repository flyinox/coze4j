package io.github.flyinox.coze4j.chat.model;

/**
 * Chat status enumeration
 */
public enum ChatStatus {
    /**
     * Chat has been created
     */
    CREATED("created"),

    /**
     * Agent is processing
     */
    IN_PROGRESS("in_progress"),

    /**
     * Chat completed successfully
     */
    COMPLETED("completed"),

    /**
     * Chat failed
     */
    FAILED("failed"),

    /**
     * Chat needs further action
     */
    REQUIRES_ACTION("requires_action"),

    /**
     * Chat was canceled
     */
    CANCELED("canceled");

    private final String value;

    ChatStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ChatStatus fromString(String text) {
        for (ChatStatus status : ChatStatus.values()) {
            if (status.value.equals(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + text);
    }
} 