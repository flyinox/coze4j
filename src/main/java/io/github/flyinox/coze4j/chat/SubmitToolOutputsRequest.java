package io.github.flyinox.coze4j.chat;
import java.util.List;

import io.github.flyinox.coze4j.chat.model.ToolOutput;
import com.google.gson.annotations.SerializedName;

/**
 * Request for submitting tool execution results
 */
public class SubmitToolOutputsRequest {
    /**
     * List of tool execution results
     */
    @SerializedName("tool_outputs")
    private List<ToolOutput> toolOutputs;

    /**
     * Whether to use streaming response
     */
    private Boolean stream;

    public static class Builder {
        private final SubmitToolOutputsRequest request;

        public Builder() {
            request = new SubmitToolOutputsRequest();
            request.stream = false; // default value
        }

        public Builder toolOutputs(List<ToolOutput> toolOutputs) {
            request.toolOutputs = toolOutputs;
            return this;
        }

        public Builder stream(Boolean stream) {
            request.stream = stream;
            return this;
        }

        public SubmitToolOutputsRequest build() {
            if (request.toolOutputs == null || request.toolOutputs.isEmpty()) {
                throw new IllegalStateException("Tool outputs are required");
            }
            return request;
        }
    }

    // Getters and setters
    public List<ToolOutput> getToolOutputs() {
        return toolOutputs;
    }

    public void setToolOutputs(List<ToolOutput> toolOutputs) {
        this.toolOutputs = toolOutputs;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }
} 