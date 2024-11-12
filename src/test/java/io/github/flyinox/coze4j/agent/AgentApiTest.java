package io.github.flyinox.coze4j.agent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.flyinox.coze4j.CozeClient;
import io.github.flyinox.coze4j.agent.model.Bot;
import io.github.flyinox.coze4j.exception.CozeErrorCode;
import io.github.flyinox.coze4j.exception.CozeException;
import io.github.flyinox.coze4j.workspaces.WorkspaceResponse;

@ExtendWith(MockitoExtension.class)
class AgentApiTest {

    private static CozeClient cozeClient;
    private static String TEST_SPACE_ID;
    private static String API_KEY;
    private static String BASE_URL;

    @BeforeAll
    static void init() throws IOException, CozeException {
        // get from environment variables
        API_KEY = System.getenv("COZE_API_KEY");
        BASE_URL = System.getenv("COZE_BASE_URL");
        cozeClient = new CozeClient(API_KEY, BASE_URL);

        // Get space id from workspace list
        WorkspaceResponse workspaceResponse = cozeClient.listWorkspaces();
        TEST_SPACE_ID = workspaceResponse.getData().getWorkspaces().get(0).getId();
    }

    // Important: the test bot cannot be deleted by API, so remember to delete it
    // manually after the test!
    @Test
    void testAgentLifecycle() throws IOException, InterruptedException, CozeException {
        // 1. Get initial published bots count
        // assert not throw exception
        PublishedBotsResponse initialListResponse = getPublishedBots(null, null);
        assertNotNull(initialListResponse);
        assertTrue(initialListResponse.isSuccess());
        int initialBotCount = initialListResponse.getData().getTotal();

        // 2. Create a new agent
        AgentCreateRequest createRequest = new AgentCreateRequest.Builder(TEST_SPACE_ID, "Test Bot from coze4j")
                .description("Test bot description from coze4j")
                .promptInfo("You are a helpful assistant from coze4j")
                .onboardingInfo("Hello! How can I help you?",
                        Arrays.asList("Question 1", "Question 2"))
                .build();

        
        AgentCreateResponse createResponse = cozeClient.createAgent(createRequest);

        assertNotNull(createResponse);
        assertTrue(createResponse.isSuccess());
        String botId = createResponse.getData().getBotId();
        assertNotNull(botId);

        // 3. Update the agent
        AgentUpdateRequest updateRequest = new AgentUpdateRequest.Builder(botId)
                .name("Updated Bot Name")
                .description("Updated description")
                .promptInfo("Updated prompt")
                .build();

        AgentCreateResponse updateResponse = cozeClient.updateAgent(updateRequest);

        assertNotNull(updateResponse);
        assertTrue(updateResponse.isSuccess());

        // 4. Test invalid bot ID
        CozeException invalidBotException = assertThrows(CozeException.class, () -> {
            cozeClient.getAgentInfo("non-existent-bot-id");
        });
        assertEquals(CozeErrorCode.INVALID_REQUEST_PARAM, invalidBotException.getErrorCode());

        // 5. Publish the agent
        AgentPublishRequest publishRequest = new AgentPublishRequest(botId);
        AgentPublishResponse.Data publishResponse = cozeClient.publishAgent(publishRequest).getData();

        assertNotNull(publishResponse);
        assertNotNull(publishResponse.getVersion());
        // 6. Get agent configuration
        Bot bot = cozeClient.getAgentInfo(botId);

        assertNotNull(bot);
        assertEquals("Updated Bot Name", bot.getName());
        assertNotNull(bot.getVersion());

        // Wait for the agent to be published, 1000ms is the minimum time for the agent
        // to be published
        Thread.sleep(1000);

        // 7. Verify published bots list
        PublishedBotsResponse finalListResponse = getPublishedBots(20, 1);
        assertNotNull(finalListResponse);
        assertTrue(finalListResponse.isSuccess());
        PublishedBotsResponse.Data listResult = finalListResponse.getData();

        // Verify bot count increased by 1
        assertEquals(initialBotCount + 1, listResult.getTotal());

        // Find our bot in the list
        boolean foundBot = listResult.getSpaceBots().stream()
                .anyMatch(b -> b.getBotId().equals(botId));
        assertTrue(foundBot, "Created bot should be in the published list");
    }

    private PublishedBotsResponse getPublishedBots(Integer pageSize, Integer pageIndex)
            throws IOException, CozeException {
        return cozeClient.getPublishedBots(
                TEST_SPACE_ID,
                pageSize,
                pageIndex);
    }
}