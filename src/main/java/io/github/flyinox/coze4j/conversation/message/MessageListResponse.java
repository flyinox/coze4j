package io.github.flyinox.coze4j.conversation.message;

import java.util.List;

import io.github.flyinox.coze4j.CozeBaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Response for message list query
 */
public class MessageListResponse extends CozeBaseResponse<List<Message>> {
    /**
     * ID of the first message in the returned list
     */
    @SerializedName("first_id")
    private String firstId;

    /**
     * ID of the last message in the returned list
     */
    @SerializedName("last_id")
    private String lastId;

    /**
     * Whether there are more messages to fetch
     */
    @SerializedName("has_more")
    private Boolean hasMore;

    public List<Message> getData() {
        return this.data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
} 