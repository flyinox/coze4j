package io.github.flyinox.coze4j.chat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.flyinox.coze4j.CozeClient;
import io.github.flyinox.coze4j.chat.ChatApi;
import io.github.flyinox.coze4j.chat.ChatRequest;
import io.github.flyinox.coze4j.chat.ChatStreamHandler;
import io.github.flyinox.coze4j.chat.model.ChatEventType;
import io.github.flyinox.coze4j.chat.model.ChatObject;
import io.github.flyinox.coze4j.conversation.message.ContentType;
import io.github.flyinox.coze4j.conversation.message.Message;
import io.github.flyinox.coze4j.conversation.message.MessageRole;
import io.github.flyinox.coze4j.conversation.message.MessageType;
import io.github.flyinox.coze4j.conversation.model.EnterMessage;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ExtendWith(MockitoExtension.class)
class ChatStreamApiTest {

    private static CozeClient cozeClient;
    private static ChatApi chatApi;
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
        chatApi = cozeClient.createService(ChatApi.class);
    }

    @Test
    void testStreamChatLifecycle() throws IOException, InterruptedException {
        // Create countdown latch for async operations
        CountDownLatch latch = new CountDownLatch(1);

        // Atomic references to store event data
        AtomicReference<String> chatId = new AtomicReference<>();
        AtomicReference<String> conversationId = new AtomicReference<>();
        AtomicBoolean receivedChatCreated = new AtomicBoolean(false);
        AtomicBoolean receivedInProgress = new AtomicBoolean(false);
        AtomicBoolean receivedAnswer = new AtomicBoolean(false);
        AtomicBoolean receivedComplete = new AtomicBoolean(false);
        StringBuilder messageContent = new StringBuilder();

        // 1. Create streaming chat request
        ChatRequest request = new ChatRequest.Builder(BOT_ID, USER_ID)
                .additionalMessages(Arrays.asList(
                        new EnterMessage.Builder(MessageRole.USER)
                                .type(MessageType.QUESTION)
                                .content("Tell me a short story about a cat")
                                .contentType(ContentType.TEXT)
                                .build()))
                .autoSaveHistory(true)
                .stream(true)
                .build();

        // 2. Start streaming chat
        chatApi.chatStream(request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ChatStreamHandler.handleStream(response.body(),
                            new ChatStreamHandler.BaseChatStreamCallback() {
                                @Override
                                public void onChatEvent(ChatEventType eventType, ChatObject chat) {
                                    System.out.println("Received chat event: " + eventType);
                                    switch (eventType) {
                                        case CHAT_CREATED:
                                            receivedChatCreated.set(true);
                                            chatId.set(chat.getId());
                                            conversationId.set(chat.getConversationId());
                                            break;
                                        case CHAT_IN_PROGRESS:
                                            receivedInProgress.set(true);
                                            break;
                                        case CHAT_COMPLETED:
                                            receivedComplete.set(true);
                                            break;
                                    }
                                }

                                @Override
                                public void onMessageEvent(ChatEventType eventType, Message message) {
                                    System.out.println("Received message event: " + eventType + 
                                        ", type: " + message.getType());
                                    if (eventType == ChatEventType.MESSAGE_DELTA) {
                                        messageContent.append(message.getContent());
                                        System.out.println("Message content: " + message.getContent());
                                    } else if (eventType == ChatEventType.MESSAGE_COMPLETED) {
                                        if (MessageType.ANSWER.getValue().equals(message.getType())) {
                                            receivedAnswer.set(true);
                                        }
                                    }
                                }

                                @Override
                                public void onError(String errorData) {
                                    fail("Received error: " + errorData);
                                    latch.countDown();
                                }

                                @Override
                                public void onComplete() {
                                    latch.countDown();
                                }
                            });
                } catch (IOException e) {
                    fail("Failed to handle stream: " + e.getMessage());
                    latch.countDown();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                fail("Stream request failed: " + t.getMessage());
                latch.countDown();
            }
        });

        // Wait for streaming to complete (with timeout)
        assertTrue(latch.await(30, TimeUnit.SECONDS), "Stream timeout");

        // 3. Verify streaming events
        assertTrue(receivedChatCreated.get(), "Should receive chat created event");
        assertTrue(receivedInProgress.get(), "Should receive in progress event");
        assertTrue(receivedAnswer.get(), "Should receive answer message");
        assertTrue(receivedComplete.get(), "Should receive complete event");
        assertFalse(messageContent.toString().isEmpty(), "Should receive message content");
        assertNotNull(chatId.get(), "Should receive chat ID");
        assertNotNull(conversationId.get(), "Should receive conversation ID");

        // TODO: 4. Test streaming tool outputs

    }
}