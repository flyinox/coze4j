package io.github.flyinox.coze4j.agent.model;

import com.google.gson.annotations.SerializedName;

/**
 * Simple bot information returned in the published bots list
 */
public class SimpleBot {
    @SerializedName("bot_id")
    private String botId;
    
    private String name;
    private String description;
    
    @SerializedName("icon_url")
    private String iconUrl;
    
    @SerializedName("publish_time")
    private String publishTime;

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
} 