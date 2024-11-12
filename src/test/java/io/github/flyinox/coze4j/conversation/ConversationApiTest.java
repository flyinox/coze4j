package io.github.flyinox.coze4j.conversation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.flyinox.coze4j.CozeClient;
import io.github.flyinox.coze4j.conversation.message.ContentType;
import io.github.flyinox.coze4j.conversation.message.MessageRole;
import io.github.flyinox.coze4j.conversation.message.MessageType;
import io.github.flyinox.coze4j.conversation.model.EnterMessage;
import retrofit2.Call;

@ExtendWith(MockitoExtension.class)
class ConversationApiTest {

    private static CozeClient cozeClient;
    private static ConversationApi conversationApi;
    private static String API_KEY;
    private static String BASE_URL;

    @BeforeAll
    static void init() throws IOException {
        // get from environment variables
        API_KEY = System.getenv("COZE_API_KEY");
        BASE_URL = System.getenv("COZE_BASE_URL");
        cozeClient = new CozeClient(API_KEY, BASE_URL);
        conversationApi = cozeClient.createService(ConversationApi.class);
    }

    @Test
    void testConversationLifecycle() throws IOException {
        // 1. Create a conversation
        ConversationCreateRequest createRequest = new ConversationCreateRequest.Builder()
                .messages(Arrays.asList(
                        new EnterMessage.Builder(MessageRole.USER)
                                .type(MessageType.QUESTION)
                                .content("Test message")
                                .contentType(ContentType.TEXT)
                                .build()))
                .metaData(Collections.singletonMap("test_key", "test_value"))
                .build();

        Call<ConversationResponse> createCall = conversationApi.createConversation(createRequest);
        ConversationResponse createResponse = createCall.execute().body();

        assertNotNull(createResponse);
        assertTrue(createResponse.isSuccess());
        String conversationId = createResponse.getData().getId();
        assertNotNull(conversationId);

        // 2. Retrieve the conversation
        Call<ConversationResponse> retrieveCall = conversationApi.getConversation(conversationId);
        ConversationResponse retrieveResponse = retrieveCall.execute().body();

        assertNotNull(retrieveResponse);
        assertTrue(retrieveResponse.isSuccess());
        ConversationResponse.Data conversation = retrieveResponse.getData();
        assertEquals(conversationId, conversation.getId());
        assertNotNull(conversation.getCreatedAt());
        assertEquals("test_value",
                conversation.getMetaData().get("test_key"));
    }
}