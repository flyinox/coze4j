package io.github.flyinox.coze4j.agent.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model configuration for the agent
 */
public class ModelInfo {
    @SerializedName("model_id")
    private String modelId;
    
    @SerializedName("model_name")
    private String modelName;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
