package io.github.flyinox.coze4j.agent.model;

/**
 * Prompt configuration for the agent
 */
public class PromptInfo {
    private String prompt;

    public PromptInfo(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
