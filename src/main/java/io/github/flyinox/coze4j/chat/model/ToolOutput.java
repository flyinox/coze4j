package io.github.flyinox.coze4j.chat.model;

import com.google.gson.annotations.SerializedName;

/**
 * Tool execution output information
 */
public class ToolOutput {
    /**
     * The ID for reporting the running results
     */
    @SerializedName("tool_call_id")
    private String toolCallId;

    /**
     * The execution result of the tool
     */
    private String output;

    public static class Builder {
        private final ToolOutput toolOutput;

        public Builder(String toolCallId, String output) {
            toolOutput = new ToolOutput();
            toolOutput.toolCallId = toolCallId;
            toolOutput.output = output;
        }

        public ToolOutput build() {
            return toolOutput;
        }
    }

    // Getters and setters
    public String getToolCallId() {
        return toolCallId;
    }

    public void setToolCallId(String toolCallId) {
        this.toolCallId = toolCallId;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
} 