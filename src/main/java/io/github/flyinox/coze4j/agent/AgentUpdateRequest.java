package io.github.flyinox.coze4j.agent;

import io.github.flyinox.coze4j.agent.model.OnboardingInfo;
import io.github.flyinox.coze4j.agent.model.PromptInfo;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AgentUpdateRequest {
    @SerializedName("bot_id")
    private String botId;
    
    private String name;
    private String description;
    
    @SerializedName("icon_file_id")
    private String iconFileId;
    
    @SerializedName("prompt_info")
    private PromptInfo promptInfo;
    
    @SerializedName("onboarding_info")
    private OnboardingInfo onboardingInfo;
    
    private KnowledgeObject knowledge;

    public static class KnowledgeObject {
        @SerializedName("dataset_ids")
        private List<String> datasetIds;
        
        @SerializedName("auto_call")
        private Boolean autoCall;
        
        @SerializedName("search_strategy")
        private Integer searchStrategy;

        // Getters and Setters
        public List<String> getDatasetIds() {
            return datasetIds;
        }

        public void setDatasetIds(List<String> datasetIds) {
            this.datasetIds = datasetIds;
        }

        public Boolean getAutoCall() {
            return autoCall;
        }

        public void setAutoCall(Boolean autoCall) {
            this.autoCall = autoCall;
        }

        public Integer getSearchStrategy() {
            return searchStrategy;
        }

        public void setSearchStrategy(Integer searchStrategy) {
            this.searchStrategy = searchStrategy;
        }
    }

    // Builder模式
    public static class Builder {
        private final AgentUpdateRequest request;

        public Builder(String botId) {
            request = new AgentUpdateRequest();
            request.botId = botId;
        }

        public Builder name(String name) {
            request.name = name;
            return this;
        }

        public Builder description(String description) {
            request.description = description;
            return this;
        }

        public Builder iconFileId(String iconFileId) {
            request.iconFileId = iconFileId;
            return this;
        }

        public Builder promptInfo(String prompt) {
            request.promptInfo = new PromptInfo(prompt);
            return this;
        }

        public Builder onboardingInfo(String prologue, List<String> suggestedQuestions) {
            OnboardingInfo info = new OnboardingInfo();
            info.setPrologue(prologue);
            info.setSuggestedQuestions(suggestedQuestions);
            request.onboardingInfo = info;
            return this;
        }

        public Builder knowledge(List<String> datasetIds, Boolean autoCall, Integer searchStrategy) {
            KnowledgeObject knowledgeObj = new KnowledgeObject();
            knowledgeObj.setDatasetIds(datasetIds);
            knowledgeObj.setAutoCall(autoCall);
            knowledgeObj.setSearchStrategy(searchStrategy);
            request.knowledge = knowledgeObj;
            return this;
        }

        public AgentUpdateRequest build() {
            if (request.name != null && (request.name.length() < 1 || request.name.length() > 20)) {
                throw new IllegalArgumentException("Name length must be between 1 and 20 characters");
            }
            if(request.description != null && request.description.length() > 500) {
                throw new IllegalArgumentException("Description length must be less than 500 characters");
            }

            return request;
        }
    }

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

    public String getIconFileId() {
        return iconFileId;
    }

    public void setIconFileId(String iconFileId) {
        this.iconFileId = iconFileId;
    }

    public PromptInfo getPromptInfo() {
        return promptInfo;
    }

    public void setPromptInfo(PromptInfo promptInfo) {
        this.promptInfo = promptInfo;
    }

    public OnboardingInfo getOnboardingInfo() {
        return onboardingInfo;
    }

    public void setOnboardingInfo(OnboardingInfo onboardingInfo) {
        this.onboardingInfo = onboardingInfo;
    }

    public KnowledgeObject getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(KnowledgeObject knowledge) {
        this.knowledge = knowledge;
    }
} 