package io.github.flyinox.coze4j.agent;

import io.github.flyinox.coze4j.CozeBaseResponse;
import com.google.gson.annotations.SerializedName;

public class AgentCreateResponse extends CozeBaseResponse<AgentCreateResponse.Data> {
    public static class Data {
        @SerializedName("bot_id")
        private String botId;

        private String name;
        private String description;

        @SerializedName("icon_url")
        private String iconUrl;

        @SerializedName("space_id")
        private String spaceId;

        @SerializedName("create_time")
        private Long createTime;

        @SerializedName("update_time")
        private Long updateTime;

        // Getters and Setters
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

        public String getSpaceId() {
            return spaceId;
        }

        public void setSpaceId(String spaceId) {
            this.spaceId = spaceId;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public Long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Long updateTime) {
            this.updateTime = updateTime;
        }
    }
}