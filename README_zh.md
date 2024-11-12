# Coze4j - Coze API 的 Java SDK

[English Documentation](README.md)

Coze4j 是一个用于访问 Coze API 的 Java SDK，提供了简单直观的接口来与 Coze 平台进行交互。

## 安装

将以下依赖添加到你的 `pom.xml` 文件中：

```xml
<dependency>
    <groupId>com.coze</groupId>
    <artifactId>coze4j</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 快速开始

### 初始化客户端

```java
String apiKey = "your-api-key";
String baseUrl = "https://api.coze.cn/";  // 或其他区域的 URL
CozeClient client = new CozeClient(apiKey, baseUrl);
```

### 基础聊天示例

```java
ChatRequest request = new ChatRequest.Builder(botId, userId)
    .additionalMessages(Arrays.asList(
        new EnterMessage.Builder(MessageRole.USER)
            .type(MessageType.QUESTION)
            .content("讲个笑话")
            .contentType(ContentType.TEXT)
            .build()))
    .build();

ChatResponse response = client.chat(request);
if (response.isSuccess()) {
    System.out.println("聊天 ID: " + response.getData().getId());
}
```

### 流式聊天示例

SDK 提供了两种处理流式响应的方式：

1. 简单流式处理（推荐用于大多数场景）：
```java
client.chatStream(request, new CozeClient.StreamCallback() {
    @Override
    public void onData(String chunk) {
        System.out.print(chunk);  // 直接接收文本块
    }

    @Override
    public void onError(Exception e) {
        System.err.println("错误: " + e.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("聊天完成！");
    }
});
```

2. 详细事件流式处理（用于高级用途）：
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
                            // 处理聊天创建事件
                            break;
                        case CHAT_IN_PROGRESS:
                            // 处理聊天进行中事件
                            break;
                        case CHAT_COMPLETED:
                            // 处理聊天完成事件
                            break;
                    }
                }

                @Override
                public void onMessageEvent(ChatEventType eventType, Message message) {
                    if (eventType == ChatEventType.MESSAGE_DELTA) {
                        // 处理消息块
                        System.out.print(message.getContent());
                    } else if (eventType == ChatEventType.MESSAGE_COMPLETED) {
                        // 处理消息完成
                    }
                }

                @Override
                public void onError(String errorData) {
                    // 处理错误
                }

                @Override
                public void onComplete() {
                    // 处理完成
                }
            });
    }
});
```

对于大多数只需要文本响应的使用场景，推荐使用简单流式处理接口。如果你需要详细控制聊天事件、消息状态或其他高级功能，可以使用详细事件流式处理方式。

## 核心功能

### 聊天操作
- 普通聊天：`client.chat(request)`
- 流式聊天：`client.chatStream(request, callback)`
- 获取聊天信息：`client.getChatInfo(conversationId, chatId)`
- 获取聊天消息：`client.getChatMessages(conversationId, chatId)`
- 提交工具输出：`client.submitToolOutputs(conversationId, chatId, request)`
- 取消聊天：`client.cancelChat(request)`

### 会话管理
- 创建会话：`client.createConversation(request)`
- 获取会话：`client.getConversation(conversationId)`
- 创建消息：`client.createMessage(conversationId, request)`
- 消息列表：`client.listMessages(conversationId, request)`
- 获取消息：`client.getMessage(conversationId, messageId)`
- 修改消息：`client.modifyMessage(conversationId, messageId, request)`
- 删除消息：`client.deleteMessage(conversationId, messageId)`

### Agent 操作
- 创建 Agent：`client.createAgent(request)`
- 更新 Agent：`client.updateAgent(request)`
- 发布 Agent：`client.publishAgent(request)`
- 获取 Agent 信息：`client.getAgentInfo(botId)`
- 获取已发布的 Agent 列表：`client.getPublishedBots(spaceId, pageSize, pageIndex)`

### 工作空间操作
- 获取工作空间列表：`client.listWorkspaces()`
- 分页获取工作空间：`client.listWorkspaces(pageNum, pageSize)`

### 文件操作
- 上传文件：`client.uploadFile(file)`
- 获取文件信息：`client.getFile(fileId)`

## 错误处理

SDK 使用 `CozeException` 进行错误处理：

```java
try {
    ChatResponse response = client.chat(request);
} catch (CozeException e) {
    System.err.println("错误代码: " + e.getErrorCode());
    System.err.println("错误信息: " + e.getMessage());
}
```

## 自定义配置

创建客户端时添加自定义请求头：

```java
Map<String, String> headers = new HashMap<>();
headers.put("Custom-Header", "value");
CozeClient client = new CozeClient(apiKey, baseUrl, headers);
```

## 示例

查看我们的示例目录获取详细用法：
- [聊天示例](src/main/java/com/coze4j/examples/ChatExample.java)

## 文档

详细的 API 文档请访问：
- 国际版：https://www.coze.com/docs/api_reference
- 中国版：https://www.coze.cn/docs/api_reference

## 许可证

本项目基于 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件。 