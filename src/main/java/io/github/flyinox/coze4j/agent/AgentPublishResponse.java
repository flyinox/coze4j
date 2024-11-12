package io.github.flyinox.coze4j.agent;

import com.google.gson.annotations.SerializedName;

import io.github.flyinox.coze4j.CozeBaseResponse;

/**
 * 发布智能体响应
 */
public class AgentPublishResponse extends CozeBaseResponse<AgentPublishResponse.Data> {
    public class Data {
        @SerializedName("bot_id")
        private String botId;

        private String version;

        public String getBotId() {
            return botId;
        }

        public void setBotId(String botId) {
            this.botId = botId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
