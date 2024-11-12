package io.github.flyinox.coze4j.agent.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Onboarding message configuration for the agent
 */
public class OnboardingInfo {
    private String prologue;
    
    @SerializedName("suggested_questions")
    private List<String> suggestedQuestions;

    public String getPrologue() {
        return prologue;
    }

    public void setPrologue(String prologue) {
        this.prologue = prologue;
    }

    public List<String> getSuggestedQuestions() {
        return suggestedQuestions;
    }

    public void setSuggestedQuestions(List<String> suggestedQuestions) {
        this.suggestedQuestions = suggestedQuestions;
    }
}

