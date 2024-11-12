package io.github.flyinox.coze4j.agent;

import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * 发布智能体请求参数
 */
public class AgentPublishRequest {
    @SerializedName("bot_id")
    private String botId;

    // for now, only API connector is supported
    @SerializedName("connector_ids")
    private List<String> connectorIds = Arrays.asList("API");

    public AgentPublishRequest(String botId) {
        this.botId = botId;
    }

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public List<String> getConnectorIds() {
        return connectorIds;
    }
} 
