package io.github.flyinox.coze4j.conversation.model;

import com.google.gson.annotations.SerializedName;

/**
 * Object string for multimodal content
 */
public class ObjectString {
    private String type;  // text, file, or image
    private String text;
    
    @SerializedName("file_id")
    private String fileId;
    
    @SerializedName("file_url")
    private String fileUrl;

    //getters
    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getFileId() {
        return fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public static class Builder {
        private final ObjectString objectString;

        public Builder(String type) {
            objectString = new ObjectString();
            objectString.type = type;
        }

        public Builder text(String text) {
            objectString.text = text;
            return this;
        }

        public Builder fileId(String fileId) {
            objectString.fileId = fileId;
            return this;
        }

        public Builder fileUrl(String fileUrl) {
            objectString.fileUrl = fileUrl;
            return this;
        }

        public ObjectString build() {
            return objectString;
        }
    }
} 