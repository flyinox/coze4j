package io.github.flyinox.coze4j.agent.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Plugin configuration for the agent
 */
public class PluginInfo {
    @SerializedName("plugin_id")
    private String pluginId;
    
    private String name;
    private String description;
    
    @SerializedName("icon_url")
    private String iconUrl;
    
    @SerializedName("api_info_list")
    private List<PluginAPI> apiInfoList;

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
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

    public List<PluginAPI> getApiInfoList() {
        return apiInfoList;
    }

    public void setApiInfoList(List<PluginAPI> apiInfoList) {
        this.apiInfoList = apiInfoList;
    }
}
