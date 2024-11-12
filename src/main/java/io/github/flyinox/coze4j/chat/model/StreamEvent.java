package io.github.flyinox.coze4j.chat.model;

/**
 * Streaming event response structure
 */
public class StreamEvent<T> {
    /**
     * Event type (e.g., "conversation.chat.created")
     */
    private String event;
    
    /**
     * Event data, can be ChatObject or Message depending on event type
     */
    private T data;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ChatEventType getEventType() {
        return ChatEventType.fromString(event);
    }
} 