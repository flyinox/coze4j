package io.github.flyinox.coze4j.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.flyinox.coze4j.CozeClient;
import io.github.flyinox.coze4j.chat.ChatRequest;
import io.github.flyinox.coze4j.chat.ChatResponse;
import io.github.flyinox.coze4j.chat.model.ChatCancelRequest;
import io.github.flyinox.coze4j.chat.model.ChatObject;
import io.github.flyinox.coze4j.chat.model.ChatStatus;
import io.github.flyinox.coze4j.conversation.message.ContentType;
import io.github.flyinox.coze4j.conversation.message.Message;
import io.github.flyinox.coze4j.conversation.message.MessageRole;
import io.github.flyinox.coze4j.conversation.message.MessageType;
import io.github.flyinox.coze4j.conversation.model.EnterMessage;
import io.github.flyinox.coze4j.exception.CozeException;

@ExtendWith(MockitoExtension.class)
class ChatApiTest {

    private static CozeClient cozeClient;
    private static String BOT_ID;
    private static String USER_ID;

    @BeforeAll
    static void init() throws IOException {
        // get from environment variables
        String apiKey = System.getenv("COZE_API_KEY");
        String baseUrl = System.getenv("COZE_BASE_URL");
        BOT_ID = System.getenv("COZE_BOT_ID");
        USER_ID = System.getenv("COZE_USER_ID");

        cozeClient = new CozeClient(apiKey, baseUrl);
    }

    @Test
    void testChatLifecycle() throws IOException, InterruptedException, CozeException {
        // 1. Start a basic chat
        ChatRequest chatRequest = new ChatRequest.Builder(BOT_ID, USER_ID)
                .additionalMessages(Arrays.asList(
                        new EnterMessage.Builder(MessageRole.USER)
                                .type(MessageType.QUESTION)
                                .content("Hello, how are you?")
                                .contentType(ContentType.TEXT)
                                .build()))
                .autoSaveHistory(true)
                .stream(false)
                .build();

        ChatResponse chatResponse = cozeClient.chat(chatRequest);

        assertNotNull(chatResponse);
        assertTrue(chatResponse.isSuccess());

        String chatId = chatResponse.getData().getId();
        String conversationId = chatResponse.getData().getConversationId();

        // Wait for chat to be processed
        Thread.sleep(2000);

        // 2. Get chat information
        ChatResponse infoResponse = cozeClient.getChatInfo(conversationId, chatId);

        assertNotNull(infoResponse);
        assertTrue(infoResponse.isSuccess());
        assertEquals(chatId, infoResponse.getData().getId());
        assertEquals(BOT_ID, infoResponse.getData().getBotId());

        // Wait for chat to complete
        waitForChatCompletion(conversationId, chatId);

        // 3. Get chat messages
        List<Message> messagesResponse = cozeClient.getChatMessages(conversationId, chatId);

        assertNotNull(messagesResponse);
        assertFalse(messagesResponse.isEmpty());

        // Verify message types
        boolean hasAnswer = false;
        boolean hasAssistantMessage = false;
        for (Message message : messagesResponse) {
            if ("assistant".equals(message.getRole()) &&
                    "answer".equals(message.getType())) {
                hasAnswer = true;
            } else if ("assistant".equals(message.getRole())) {
                hasAssistantMessage = true;
            }
        }
        assertTrue(hasAnswer, "Should contain assistant answer");
        assertTrue(hasAssistantMessage, "Should contain assistant message");

        // TODO: 4. Test tool call scenario

        // 5. Test chat cancellation
        ChatRequest longRequest = new ChatRequest.Builder(BOT_ID, USER_ID)
                .additionalMessages(Arrays.asList(
                        new EnterMessage.Builder(MessageRole.USER)
                                .type(MessageType.QUESTION)
                                .content("Write a very long story about adventures...")
                                .contentType(ContentType.TEXT)
                                .build()))
                .autoSaveHistory(true)
                .stream(false)
                .build();

        ChatResponse longResponse = cozeClient.chat(longRequest);
        String cancelChatId = longResponse.getData().getId();
        String cancelConversationId = longResponse.getData().getConversationId();

        // Wait briefly to ensure chat is processing
        Thread.sleep(1000);

        // Cancel the chat
        ChatCancelRequest cancelRequest = new ChatCancelRequest.Builder(
                cancelChatId,
                cancelConversationId).build();

        ChatObject cancelResponse = cozeClient.cancelChat(cancelRequest);

        assertNotNull(cancelResponse);
        assertEquals(ChatStatus.CANCELED.getValue(),
                cancelResponse.getStatus());

        // Verify final chat status
        ChatResponse finalResponse = cozeClient.getChatInfo(cancelConversationId, cancelChatId);

        assertNotNull(finalResponse);
        assertTrue(finalResponse.isSuccess());
        assertEquals(ChatStatus.CANCELED.getValue(),
                finalResponse.getData().getStatus());
    }

    private void waitForChatCompletion(String conversationId, String chatId)
            throws IOException, InterruptedException, CozeException {
        int maxAttempts = 30;
        int attempt = 0;
        while (attempt < maxAttempts) {
            ChatResponse response = cozeClient.getChatInfo(conversationId, chatId);
            if (response != null && response.isSuccess()) {
                String status = response.getData().getStatus();
                if (ChatStatus.COMPLETED.getValue().equals(status) ||
                        ChatStatus.FAILED.getValue().equals(status)) {
                    break;
                }
            }
            Thread.sleep(1000);
            attempt++;
        }
    }
}