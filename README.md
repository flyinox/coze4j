# Coze4j - Java SDK for Coze API

[中文文档](README_zh.md)

A Java SDK for interacting with the Coze API, providing a simple and intuitive interface.

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.coze</groupId>
    <artifactId>coze4j</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Quick Start

### Initialize Client

```java
String apiKey = "your-api-key";
String baseUrl = "https://api.coze.cn/";  // or other regional URL
CozeClient client = new CozeClient(apiKey, baseUrl);
```

### Basic Chat Example

```java
ChatRequest request = new ChatRequest.Builder(botId, userId)
    .additionalMessages(Arrays.asList(
        new EnterMessage.Builder(MessageRole.USER)
            .type(MessageType.QUESTION)
            .content("Tell me a joke")
            .contentType(ContentType.TEXT)
            .build()))
    .build();

ChatResponse response = client.chat(request);
if (response.isSuccess()) {
    System.out.println("Chat ID: " + response.getData().getId());
}
```

### Streaming Chat Example

The SDK provides two ways to handle streaming responses:

1. Simple Streaming (Recommended for most cases):
```java
client.chatStream(request, new CozeClient.StreamCallback() {
    @Override
    public void onData(String chunk) {
        System.out.print(chunk);  // Receive text chunks directly
    }

    @Override
    public void onError(Exception e) {
        System.err.println("Error: " + e.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Chat completed!");
    }
});
```

2. Detailed Event Streaming (For advanced usage):
```java
ChatApi chatApi = client.createService(ChatApi.class);
chatApi.chatStream(request).enqueue(new Callback<ResponseBody>() {
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        ChatStreamHandler.handleStream(response.body(),
            new ChatStreamHandler.BaseChatStreamCallback() {
                @Override
                public void onChatEvent(ChatEventType eventType, ChatObject chat) {
                    switch (eventType) {
                        case CHAT_CREATED:
                            // Handle chat creation
                            break;
                        case CHAT_IN_PROGRESS:
                            // Handle chat progress
                            break;
                        case CHAT_COMPLETED:
                            // Handle chat completion
                            break;
                    }
                }

                @Override
                public void onMessageEvent(ChatEventType eventType, Message message) {
                    if (eventType == ChatEventType.MESSAGE_DELTA) {
                        // Handle message chunk
                        System.out.print(message.getContent());
                    } else if (eventType == ChatEventType.MESSAGE_COMPLETED) {
                        // Handle message completion
                    }
                }

                @Override
                public void onError(String errorData) {
                    // Handle error
                }

                @Override
                public void onComplete() {
                    // Handle completion
                }
            });
    }
});
```

The simple streaming interface is recommended for most use cases where you only need the text response. If you need detailed control over chat events, message states, or other advanced features, use the detailed event streaming approach.

## Core Features

### Chat Operations
- Regular chat: `client.chat(request)`
- Streaming chat: `client.chatStream(request, callback)`
- Get chat info: `client.getChatInfo(conversationId, chatId)`
- Get chat messages: `client.getChatMessages(conversationId, chatId)`
- Submit tool outputs: `client.submitToolOutputs(conversationId, chatId, request)`
- Cancel chat: `client.cancelChat(request)`

### Conversation Management
- Create conversation: `client.createConversation(request)`
- Get conversation: `client.getConversation(conversationId)`
- Create message: `client.createMessage(conversationId, request)`
- List messages: `client.listMessages(conversationId, request)`
- Get message: `client.getMessage(conversationId, messageId)`
- Modify message: `client.modifyMessage(conversationId, messageId, request)`
- Delete message: `client.deleteMessage(conversationId, messageId)`

### Agent Operations
- Create agent: `client.createAgent(request)`
- Update agent: `client.updateAgent(request)`
- Publish agent: `client.publishAgent(request)`
- Get agent info: `client.getAgentInfo(botId)`
- List published agents: `client.getPublishedBots(spaceId, pageSize, pageIndex)`

### Workspace Operations
- List workspaces: `client.listWorkspaces()`
- List workspaces with pagination: `client.listWorkspaces(pageNum, pageSize)`

### File Operations
- Upload file: `client.uploadFile(file)`
- Get file info: `client.getFile(fileId)`

## Error Handling

The SDK uses `CozeException` for error handling:

```java
try {
    ChatResponse response = client.chat(request);
} catch (CozeException e) {
    System.err.println("Error code: " + e.getErrorCode());
    System.err.println("Error message: " + e.getMessage());
}
```

## Custom Configuration

Add custom headers when creating the client:

```java
Map<String, String> headers = new HashMap<>();
headers.put("Custom-Header", "value");
CozeClient client = new CozeClient(apiKey, baseUrl, headers);
```

## Examples

Check our examples directory for detailed usage:
- [Chat Examples](src/main/java/com/coze4j/examples/ChatExample.java)

## Documentation

For detailed API documentation, visit:
- International: https://www.coze.com/docs/api_reference
- China: https://www.coze.cn/docs/api_reference

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details. 