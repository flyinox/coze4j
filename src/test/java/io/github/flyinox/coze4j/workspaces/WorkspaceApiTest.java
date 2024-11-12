package io.github.flyinox.coze4j.workspaces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.flyinox.coze4j.CozeClient;
import io.github.flyinox.coze4j.workspaces.WorkspaceApi;
import io.github.flyinox.coze4j.workspaces.WorkspaceResponse;

import retrofit2.Call;
import retrofit2.Response;

@ExtendWith(MockitoExtension.class)
class WorkspaceApiTest {

    private CozeClient cozeClient;
    private WorkspaceApi workspaceApi;
    private static String API_KEY;
    private static String BASE_URL;

    @BeforeAll
    static void init() throws IOException {
        // get from environment variables
        API_KEY = System.getenv("COZE_API_KEY");
        BASE_URL = System.getenv("COZE_BASE_URL");
    }

    @BeforeEach
    void setUp() {
        cozeClient = new CozeClient(API_KEY, BASE_URL);
        workspaceApi = cozeClient.createService(WorkspaceApi.class);
    }

    @Test
    void testListWorkspaces() throws IOException {
        // default parameters test
        Call<WorkspaceResponse> call = workspaceApi.listWorkspaces(1, 20);
        WorkspaceResponse response = call.execute().body();

        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.isSuccess());
    }

    @Test
    void testListWorkspacesWithCustomPagination() throws IOException {
        // custom pagination test
        Call<WorkspaceResponse> call = workspaceApi.listWorkspaces(2, 30);
        WorkspaceResponse response = call.execute().body();

        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.isSuccess());
    }

    @Test
    void testListWorkspacesWithNullParameters() throws IOException {
        // null parameters test
        Call<WorkspaceResponse> call = workspaceApi.listWorkspaces(null, null);
        WorkspaceResponse response = call.execute().body();

        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.isSuccess());
    }

    @Test
    void testListWorkspacesWithInvalidAuth() throws IOException {
        cozeClient = new CozeClient("invalid-api-key", BASE_URL);
        workspaceApi = cozeClient.createService(WorkspaceApi.class);
        Call<WorkspaceResponse> call = workspaceApi.listWorkspaces(1, 20);
        Response<WorkspaceResponse> response = call.execute();
        assertEquals(401, response.code());
    }
}