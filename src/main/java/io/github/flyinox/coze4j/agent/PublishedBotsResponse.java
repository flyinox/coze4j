package io.github.flyinox.coze4j.agent;

import java.util.List;

import io.github.flyinox.coze4j.CozeBaseResponse;
import io.github.flyinox.coze4j.agent.model.SimpleBot;
import com.google.gson.annotations.SerializedName;

/**
 * Response for published bots list query
 */
public class PublishedBotsResponse extends CozeBaseResponse<PublishedBotsResponse.Data> {
    public static class Data {
        @SerializedName("space_bots")
        private List<SimpleBot> spaceBots;

        private Integer total;

        public List<SimpleBot> getSpaceBots() {
            return spaceBots;
        }

        public void setSpaceBots(List<SimpleBot> spaceBots) {
            this.spaceBots = spaceBots;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }
    }
}