package io.github.flyinox.coze4j.file;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Utility class for file upload operations
 */
public class FileUploader {
    
    /**
     * Create MultipartBody.Part from File
     *
     * @param file The file to upload
     * @return MultipartBody.Part for the file
     * @throws UnsupportedEncodingException 
     */
    public static MultipartBody.Part createFilePart(File file) throws UnsupportedEncodingException {
        // get mime type
        String mimeType = getMimeType(file);
        MediaType mediaType = MediaType.parse(mimeType);

        // create request body
        RequestBody requestFile = RequestBody.create(mediaType, file);

        // encode file name
        String encodedFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.toString());

        // create multipart body part
        return MultipartBody.Part.createFormData("file", encodedFileName, requestFile);
    }

    private static String getMimeType(File file) {
        String fileName = file.getName();
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1).toLowerCase();
        }

        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "pdf":
                return "application/pdf";
            case "txt":
                return "text/plain";
            default:
                return "application/octet-stream"; // 默认类型
        }
    }

    /**
     * Validate file before upload
     *
     * @param file The file to validate
     * @throws IllegalArgumentException if file is invalid
     */
    public static void validateFile(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }

        if (file.length() > 512 * 1024 * 1024) { // 512 MB
            throw new IllegalArgumentException("File size exceeds 512 MB limit");
        }

        String fileName = file.getName().toLowerCase();
        if (!isValidFileExtension(fileName)) {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }

    /**
     * Check if file extension is supported
     *
     * @param fileName The name of the file
     * @return true if file extension is supported
     */
    private static boolean isValidFileExtension(String fileName) {
        return fileName.endsWith(".doc") || fileName.endsWith(".docx") ||
               fileName.endsWith(".xls") || fileName.endsWith(".xlsx") ||
               fileName.endsWith(".ppt") || fileName.endsWith(".pptx") ||
               fileName.endsWith(".pdf") || fileName.endsWith(".numbers") ||
               fileName.endsWith(".csv") || fileName.endsWith(".jpg") ||
               fileName.endsWith(".jpg2") || fileName.endsWith(".png") ||
               fileName.endsWith(".gif") || fileName.endsWith(".webp") ||
               fileName.endsWith(".heic") || fileName.endsWith(".heif") ||
               fileName.endsWith(".bmp") || fileName.endsWith(".pcd") ||
               fileName.endsWith(".tiff");
    }
} 