package io.github.flyinox.coze4j.agent.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Knowledge configuration for the agent
 */
public class Knowledge {
    @SerializedName("knowledge_infos")
    private List<KnowledgeInfo> knowledgeInfos;

    public List<KnowledgeInfo> getKnowledgeInfos() {
        return knowledgeInfos;
    }

    public void setKnowledgeInfos(List<KnowledgeInfo> knowledgeInfos) {
        this.knowledgeInfos = knowledgeInfos;
    }
}
