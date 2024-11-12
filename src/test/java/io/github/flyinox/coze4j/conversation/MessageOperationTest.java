package io.github.flyinox.coze4j.conversation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.flyinox.coze4j.CozeBaseResponse;
import io.github.flyinox.coze4j.CozeClient;
import io.github.flyinox.coze4j.conversation.message.ContentType;
import io.github.flyinox.coze4j.conversation.message.Message;
import io.github.flyinox.coze4j.conversation.message.MessageCreateRequest;
import io.github.flyinox.coze4j.conversation.message.MessageListRequest;
import io.github.flyinox.coze4j.conversation.message.MessageListResponse;
import io.github.flyinox.coze4j.conversation.message.MessageModifyRequest;
import io.github.flyinox.coze4j.conversation.message.MessageRole;
import io.github.flyinox.coze4j.conversation.message.MessageType;
import io.github.flyinox.coze4j.conversation.model.EnterMessage;

@ExtendWith(MockitoExtension.class)
class MessageOperationTest {

    private static CozeClient cozeClient;
    private static ConversationApi conversationApi;
    private static String API_KEY;
    private static String BASE_URL;
    private static String conversationId;

    @BeforeAll
    static void init() throws IOException {
        // get from environment variables
        API_KEY = System.getenv("COZE_API_KEY");
        BASE_URL = System.getenv("COZE_BASE_URL");
        cozeClient = new CozeClient(API_KEY, BASE_URL);
        conversationApi = cozeClient.createService(ConversationApi.class);

        // Create a test conversation
        ConversationCreateRequest createRequest = new ConversationCreateRequest.Builder()
                .messages(Arrays.asList(
                        new EnterMessage.Builder(MessageRole.USER)
                                .type(MessageType.QUESTION)
                                .content("Initial message for message operations test")
                                .contentType(ContentType.TEXT)
                                .build()))
                .build();

        ConversationResponse response = conversationApi.createConversation(createRequest).execute().body();
        conversationId = response.getData().getId();
    }

    @Test
    void testMessageLifecycle() throws IOException, InterruptedException {
        // 1. Get initial message list
        MessageListRequest initialListRequest = new MessageListRequest.Builder()
                .limit(10)
                .order("desc")
                .build();

        MessageListResponse initialListResponse = conversationApi.listMessages(conversationId, initialListRequest)
                .execute().body();

        assertNotNull(initialListResponse);
        assertTrue(initialListResponse.isSuccess());
        int initialMessageCount = initialListResponse.getData().size();

        // 2. Create a new message
        Map<String, String> metadata = new HashMap<>();
        metadata.put("test_key", "test_value");

        MessageCreateRequest createRequest = new MessageCreateRequest.Builder("user",
                "Test message for lifecycle test", "text")
                .metaData(metadata)
                .build();

        CozeBaseResponse<Message> createResponse = conversationApi.createMessage(conversationId, createRequest)
                .execute().body();

        assertNotNull(createResponse);
        assertTrue(createResponse.isSuccess());
        String messageId = createResponse.getData().getId();

        // Wait for the message to be created
        Thread.sleep(2000);

        // 3. Verify message list after creation
        MessageListRequest verifyListRequest = new MessageListRequest.Builder()
                .limit(10)
                .order("desc")
                .build();

        MessageListResponse verifyListResponse = conversationApi.listMessages(conversationId, verifyListRequest)
                .execute().body();

        assertNotNull(verifyListResponse);
        assertTrue(verifyListResponse.isSuccess());
        List<Message> messages = verifyListResponse.getData();
        assertEquals(initialMessageCount + 1, messages.size());

        // Verify the newly created message is in the list
        Optional<Message> newMessage = messages.stream()
                .filter(m -> m.getId().equals(messageId))
                .findFirst();

        assertTrue(newMessage.isPresent(), "New message should be in the list");
        assertEquals("Test message for lifecycle test", newMessage.get().getContent());

        // 4. Get message details
        CozeBaseResponse<Message> getResponse = conversationApi.getMessage(conversationId, messageId).execute().body();

        assertNotNull(getResponse);
        assertTrue(getResponse.isSuccess());
        assertEquals("Test message for lifecycle test", getResponse.getData().getContent());
        assertEquals("test_value", getResponse.getData().getMetaData().get("test_key"));

        // 5. Modify the message
        MessageModifyRequest modifyRequest = new MessageModifyRequest.Builder()
                .content("Modified test message")
                .contentType("text")
                .build();

        CozeBaseResponse<Message> modifyResponse = conversationApi
                .modifyMessage(conversationId, messageId, modifyRequest)
                .execute().body();

        assertNotNull(modifyResponse);
        assertTrue(modifyResponse.isSuccess());

        // Wait for the message to be modified
        Thread.sleep(1000);

        // 6. Verify modified message details
        CozeBaseResponse<Message> verifyModifyResponse = conversationApi.getMessage(conversationId, messageId).execute()
                .body();

        assertNotNull(verifyModifyResponse);
        assertTrue(verifyModifyResponse.isSuccess());
        assertEquals("Modified test message", verifyModifyResponse.getData().getContent());
        assertTrue(verifyModifyResponse.getData().getUpdatedAt() > verifyModifyResponse.getData().getCreatedAt());

        // 7. Delete the message
        CozeBaseResponse<Object> deleteResponse = conversationApi.deleteMessage(conversationId, messageId).execute()
                .body();

        assertNotNull(deleteResponse);
        assertTrue(deleteResponse.isSuccess());

        // Wait for the message to be deleted
        Thread.sleep(1000);

        // 8. Verify message is deleted
        CozeBaseResponse<Message> verifyDeleteResponse = conversationApi.getMessage(conversationId, messageId).execute()
                .body();
        // FIXME: it looks like message be deleted can also be retrieved, need to check if this is expected behavior
        assertTrue(verifyDeleteResponse.isSuccess());

        // Wait for the message to be deleted
        Thread.sleep(1000);

        // 9. Verify message list after deletion
        MessageListResponse finalListResponse = conversationApi.listMessages(conversationId, verifyListRequest)
                .execute().body();

        assertNotNull(finalListResponse);
        assertTrue(finalListResponse.isSuccess());
        assertEquals(initialMessageCount,
                finalListResponse.getData().size());

        boolean messageExists = finalListResponse.getData().stream()
                .anyMatch(m -> m.getId().equals(messageId));
        assertFalse(messageExists, "Deleted message should not appear in message list");
    }
}