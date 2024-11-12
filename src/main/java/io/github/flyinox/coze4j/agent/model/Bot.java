package io.github.flyinox.coze4j.agent.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Bot {

    @SerializedName("bot_id")
    private String botId;
    
    private String name;
    private String description;
    
    @SerializedName("icon_url")
    private String iconUrl;
    
    @SerializedName("create_time")
    private Integer createTime;
    
    @SerializedName("update_time")
    private Integer updateTime;
    
    private String version;
    
    @SerializedName("prompt_info")
    private PromptInfo promptInfo;
    
    @SerializedName("onboarding_info")
    private OnboardingInfo onboardingInfo;
    
    @SerializedName("bot_mode")
    private Integer botMode;
    
    @SerializedName("plugin_info_list")
    private List<PluginInfo> pluginInfoList;
    
    @SerializedName("model_info")
    private ModelInfo modelInfo;
    
    private Knowledge knowledge;

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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public Integer getBotMode() {
        return botMode;
    }

    public void setBotMode(Integer botMode) {
        this.botMode = botMode;
    }

    public List<PluginInfo> getPluginInfoList() {
        return pluginInfoList;
    }

    public void setPluginInfoList(List<PluginInfo> pluginInfoList) {
        this.pluginInfoList = pluginInfoList;
    }

    public ModelInfo getModelInfo() {
        return modelInfo;
    }

    public void setModelInfo(ModelInfo modelInfo) {
        this.modelInfo = modelInfo;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }
}
