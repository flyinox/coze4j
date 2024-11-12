package io.github.flyinox.coze4j.agent;

import java.util.List;

import io.github.flyinox.coze4j.agent.model.OnboardingInfo;
import io.github.flyinox.coze4j.agent.model.PromptInfo;
import com.google.gson.annotations.SerializedName;

public class AgentCreateRequest {
    @SerializedName("space_id")
    private String spaceId;
    
    private String name;
    private String description;
    
    @SerializedName("icon_file_id")
    private String iconFileId;
    
    @SerializedName("prompt_info")
    private PromptInfo promptInfo;
    
    @SerializedName("onboarding_info")
    private OnboardingInfo onboardingInfo;

    //getters
    public String getSpaceId() {
        return spaceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIconFileId() {
        return iconFileId;
    }

    public PromptInfo getPromptInfo() {
        return promptInfo;
    }

    public OnboardingInfo getOnboardingInfo() {
        return onboardingInfo;
    }

    public static class Builder {
        private String spaceId;
        private String name;
        private String description;
        private String iconFileId;
        private PromptInfo promptInfo;
        private OnboardingInfo onboardingInfo;

        public Builder(String spaceId, String name) {
            this.spaceId = spaceId;
            this.name = name;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder iconFileId(String iconFileId) {
            this.iconFileId = iconFileId;
            return this;
        }

        public Builder promptInfo(PromptInfo promptInfo) {
            this.promptInfo = promptInfo;
            return this;
        }

        public Builder promptInfo(String prompt) {
            this.promptInfo = new PromptInfo(prompt);
            return this;
        }

        public Builder onboardingInfo(OnboardingInfo onboardingInfo) {
            this.onboardingInfo = onboardingInfo;
            return this;
        }

        public Builder onboardingInfo(String welcomeMessage, List<String> onboardingQuestions) {
            this.onboardingInfo = new OnboardingInfo();
            this.onboardingInfo.setPrologue(welcomeMessage);
            this.onboardingInfo.setSuggestedQuestions(onboardingQuestions);
            return this;
        }   

        public AgentCreateRequest build() {
            AgentCreateRequest request = new AgentCreateRequest();
            request.spaceId = this.spaceId;
            request.name = this.name;
            request.description = this.description;
            request.iconFileId = this.iconFileId;
            request.promptInfo = this.promptInfo;
            request.onboardingInfo = this.onboardingInfo;
            return request;
        }
    }
} 