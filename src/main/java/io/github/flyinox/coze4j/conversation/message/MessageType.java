package io.github.flyinox.coze4j.conversation.message;

/**
 * Message type enumeration
 * Different scenarios support different message types
 */
public enum MessageType {
    QUESTION("question"),
    ANSWER("answer"),
    FUNCTION_CALL("function_call"),
    TOOL_OUTPUT("tool_output"),
    TOOL_RESPONSE("tool_response"),
    FOLLOW_UP("follow_up"),
    KNOWLEDGE("knowledge"),
    VERBOSE("verbose");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Check if the message type is valid for the given role and auto save history setting
     *
     * @param role The message role
     * @param autoSaveHistory Whether auto save history is enabled
     * @return true if the type is valid for the given parameters
     */
    public boolean isValidFor(MessageRole role, boolean autoSaveHistory) {
        switch (this) {
            case QUESTION:
                return role == MessageRole.USER;
            case ANSWER:
                return role == MessageRole.ASSISTANT;
            case FUNCTION_CALL:
            case TOOL_OUTPUT:
            case TOOL_RESPONSE:
                return role == MessageRole.ASSISTANT && !autoSaveHistory;
            case FOLLOW_UP:
            case KNOWLEDGE:
            case VERBOSE:
                return role == MessageRole.ASSISTANT;
            default:
                return false;
        }
    }
} 