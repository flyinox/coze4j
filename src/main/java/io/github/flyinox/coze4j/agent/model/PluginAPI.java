package io.github.flyinox.coze4j.agent.model;

import com.google.gson.annotations.SerializedName;

/**
 * Plugin API tool information
 */
public class PluginAPI {
    @SerializedName("api_id")
    private String apiId;
    
    private String name;
    private String description;

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
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
}
