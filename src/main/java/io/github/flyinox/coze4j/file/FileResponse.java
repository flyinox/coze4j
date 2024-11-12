package io.github.flyinox.coze4j.file;

import io.github.flyinox.coze4j.CozeBaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Response data for file upload
 */
public class FileResponse extends CozeBaseResponse<FileResponse.Data> {
    public class Data {
        /**
         * The ID of the uploaded file
     */
    private String id;

    /**
     * The total byte size of the file
     */
    private Integer bytes;

    /**
     * The upload time of the file (Unix timestamp in seconds)
     */
    @SerializedName("created_at")
    private Integer createdAt;

    /**
     * The name of the file
     */
    @SerializedName("file_name")
    private String fileName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        }
    }
} 