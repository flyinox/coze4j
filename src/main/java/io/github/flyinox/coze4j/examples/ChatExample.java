package io.github.flyinox.coze4j.examples;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.github.flyinox.coze4j.CozeClient;
import io.github.flyinox.coze4j.CozeClient.StreamCallback;
import io.github.flyinox.coze4j.agent.AgentApi;
import io.github.flyinox.coze4j.agent.AgentCreateRequest;
import io.github.flyinox.coze4j.agent.AgentCreateResponse;
import io.github.flyinox.coze4j.chat.ChatRequest;
import io.github.flyinox.coze4j.chat.ChatResponse;
import io.github.flyinox.coze4j.chat.model.ChatStatus;
import io.github.flyinox.coze4j.conversation.message.ContentType;
import io.github.flyinox.coze4j.conversation.message.Message;
import io.github.flyinox.coze4j.conversation.message.MessageRole;
import io.github.flyinox.coze4j.conversation.message.MessageType;
import io.github.flyinox.coze4j.conversation.model.EnterMessage;
import io.github.flyinox.coze4j.workspaces.WorkspaceResponse;

/**
 * Example usage of Coze4j library
 */
public class ChatExample {

    public static void main(String[] args) throws Exception {
        // get basic config from environment variables
        String apiKey = System.getenv("COZE_API_KEY");
        String botId = System.getenv("COZE_BOT_ID");
        String userId = System.getenv("COZE_USER_ID");
        // get from env or default
        String baseUrl = System.getenv("COZE_BASE_URL");
        if (baseUrl == null) {
            baseUrl = "https://api.coze.com/"; // or "https://api.coze.cn/" depending on your region
        }

        CozeClient client = new CozeClient(apiKey, baseUrl);

        // get workspace id
        WorkspaceResponse workspaceResponse = client.listWorkspaces();
        String workspaceId = workspaceResponse.getData().getWorkspaces().get(0).getId();

        // if you don't have a bot, create one
        if (botId == null) {
            botId = createBot(client, workspaceId);
        }

        // Example 1: Simple non-streaming chat
        System.out.println("=== Non-streaming Chat Example ===");
        simpleChat(client, botId, userId);

        // Example 2: Streaming chat
        System.out.println("\n=== Streaming Chat Example ===");
        streamingChat(client, botId, userId);
    }

    /**
     * Example of simple non-streaming chat
     */
    private static void simpleChat(CozeClient client, String botId, String userId) {
        try {
            // Create chat request
            ChatRequest request = new ChatRequest.Builder(botId, userId)
                    .additionalMessages(Arrays.asList(
                            new EnterMessage.Builder(MessageRole.USER)
                                    .type(MessageType.QUESTION)
                                    .content("Tell me a joke")
                                    .contentType(ContentType.TEXT)
                                    .build()))
                    .autoSaveHistory(true)
                    .stream(false)
                    .build();

            // Send request
            ChatResponse response = client.chat(request);
            if (response == null || !response.isSuccess()) {
                System.err.println("Chat request failed");
                return;
            }

            String conversationId = response.getData().getConversationId();
            String chatId = response.getData().getId();

            int maxAttempts = 30; // max attempts to wait for completion
            int attempt = 0;
            while (attempt < maxAttempts) {
                ChatResponse finalResponse = client.getChatInfo(conversationId, chatId);
                if (finalResponse != null && finalResponse.isSuccess()) {
                    String status = finalResponse.getData().getStatus();
                    if (ChatStatus.COMPLETED.getValue().equals(status) ||
                            ChatStatus.FAILED.getValue().equals(status)) {
                        // get message
                        List<Message> messages = client.getChatMessages(conversationId, chatId);
                        Message message = messages.get(0);
                        System.out.println("Chat completed: " + message.getContent());
                        break;
                    }
                }
                Thread.sleep(1000);
                attempt++;
            }
        } catch (Exception e) {
            System.err.println("Error in simple chat: " + e.getMessage());
        }
    }

    /**
     * Example of streaming chat
     */
    private static void streamingChat(CozeClient client, String botId, String userId) {
        try {
            // Create chat request
            ChatRequest request = new ChatRequest.Builder(botId, userId)
                    .additionalMessages(Arrays.asList(
                            new EnterMessage.Builder(MessageRole.USER)
                                    .type(MessageType.QUESTION)
                                    .content("Write a short story about a cat")
                                    .contentType(ContentType.TEXT)
                                    .build()))
                    .autoSaveHistory(true)
                    .stream(true)
                    .build();

            // Create latch for async operation
            CountDownLatch latch = new CountDownLatch(1);

            // Start streaming chat
            client.chatStream(request, new StreamCallback() {
                @Override
                public void onData(String chunk) {
                    System.out.print(chunk);
                }

                @Override
                public void onError(Exception e) {
                    System.err.println("\nError occurred: " + e.getMessage());
                    latch.countDown();  // 确保在错误时释放锁
                }

                @Override
                public void onComplete() {
                    System.out.println("\nChat completed!");
                    latch.countDown();  // 正常完成时释放锁
                }
            });

            if (!latch.await(30, TimeUnit.SECONDS)) {
                System.err.println("Timeout waiting for response");
            }
        } catch (Exception e) {
            System.err.println("Error in streaming chat: " + e.getMessage());
        }
    }

    private static String createBot(CozeClient client, String workspaceId) throws IOException {
        AgentApi agentApi = client.createService(AgentApi.class);
        AgentCreateRequest request = new AgentCreateRequest.Builder(workspaceId, "test-bot")
                .description("test-bot")
                .build();
        AgentCreateResponse agentResponse = agentApi.createAgent(request).execute().body();
        return agentResponse.getData().getBotId();
    }
}