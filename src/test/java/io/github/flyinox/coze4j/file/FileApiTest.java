package io.github.flyinox.coze4j.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import io.github.flyinox.coze4j.CozeClient;
import okhttp3.MultipartBody;

class FileApiTest {
    
    private static CozeClient cozeClient;
    private static FileApi fileApi;
    private static String API_KEY;
    private static String BASE_URL;

    @BeforeAll
    static void init() throws IOException {
        // get from environment variables
        API_KEY = System.getenv("COZE_API_KEY");
        BASE_URL = System.getenv("COZE_BASE_URL");
        cozeClient = new CozeClient(API_KEY, BASE_URL);
        fileApi = cozeClient.createService(FileApi.class);
    }

    @Test
    void testFileLifecycle(@TempDir Path tempDir) throws IOException, InterruptedException {
        // 1. First upload a file
        File testFile = tempDir.resolve("test.png").toFile();
        createTestImage(testFile);

        MultipartBody.Part filePart = FileUploader.createFilePart(testFile);
        FileResponse uploadResponse = fileApi.uploadFile(filePart).execute().body();

        assertNotNull(uploadResponse);
        assertTrue(uploadResponse.isSuccess());
        String fileId = uploadResponse.getData().getId();

        // Wait for the file to be uploaded
        Thread.sleep(1000);

        // 2. Get file information
        FileResponse getResponse = fileApi.getFile(fileId).execute().body();

        // 3. Verify file information
        assertNotNull(getResponse);
        assertTrue(getResponse.isSuccess());
        FileResponse.Data fileInfo = getResponse.getData();
        
        assertEquals(fileId, fileInfo.getId());
        assertEquals("test.png", fileInfo.getFileName());
        assertEquals(uploadResponse.getData().getBytes(), fileInfo.getBytes());
    }

    /**
     * Create a valid test PNG image
     * @param file The file to create
     * @throws IOException if an I/O error occurs
     */
    private void createTestImage(File file) throws IOException {
        // Create a 100x100 pixel image
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        // Save the image as PNG
        ImageIO.write(image, "PNG", file);
    }
} 