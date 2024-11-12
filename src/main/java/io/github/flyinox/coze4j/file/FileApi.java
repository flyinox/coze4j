package io.github.flyinox.coze4j.file;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * API for file operations
 */
public interface FileApi {
    /**
     * Upload a file to Coze platform
     * Requires retrieveFile permission
     * Supported formats:
     * - Documents: DOC, DOCX, XLS, XLSX, PPT, PPTX, PDF, Numbers, CSV
     * - Images: JPG, JPG2, PNG, GIF, WEBP, HEIC, HEIF, BMP, PCD, TIFF
     * Max file size: 512 MB
     * Files expire after 3 months
     *
     * @param file The file to upload (multipart/form-data)
     * @return Upload result including file ID and information
     */
    @Multipart
    @POST("v1/files/upload")
    Call<FileResponse> uploadFile(
        @Part MultipartBody.Part file
    );

    /**
     * Get information about a specific file
     * Requires chat permission
     *
     * @param fileId The ID of the file to retrieve
     * @return File information including size, name, and creation time
     */
    @GET("v1/files/retrieve")
    Call<FileResponse> getFile(
        @Query("file_id") String fileId
    );
} 