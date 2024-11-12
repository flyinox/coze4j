package io.github.flyinox.coze4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import io.github.flyinox.coze4j.agent.AgentApi;
import io.github.flyinox.coze4j.agent.AgentCreateRequest;
import io.github.flyinox.coze4j.agent.AgentCreateResponse;
import io.github.flyinox.coze4j.agent.AgentPublishRequest;
import io.github.flyinox.coze4j.agent.AgentPublishResponse;
import io.github.flyinox.coze4j.agent.AgentUpdateRequest;
import io.github.flyinox.coze4j.agent.PublishedBotsResponse;
import io.github.flyinox.coze4j.agent.model.Bot;
import io.github.flyinox.coze4j.auth.AuthApi;
import io.github.flyinox.coze4j.chat.ChatApi;
import io.github.flyinox.coze4j.chat.ChatRequest;
import io.github.flyinox.coze4j.chat.ChatResponse;
import io.github.flyinox.coze4j.chat.ChatStreamHandler;
import io.github.flyinox.coze4j.chat.SubmitToolOutputsRequest;
import io.github.flyinox.coze4j.chat.model.ChatCancelRequest;
import io.github.flyinox.coze4j.chat.model.ChatEventType;
import io.github.flyinox.coze4j.chat.model.ChatObject;
import io.github.flyinox.coze4j.conversation.ConversationApi;
import io.github.flyinox.coze4j.conversation.ConversationCreateRequest;
import io.github.flyinox.coze4j.conversation.ConversationResponse;
import io.github.flyinox.coze4j.conversation.message.Message;
import io.github.flyinox.coze4j.conversation.message.MessageCreateRequest;
import io.github.flyinox.coze4j.conversation.message.MessageListRequest;
import io.github.flyinox.coze4j.conversation.message.MessageListResponse;
import io.github.flyinox.coze4j.conversation.message.MessageModifyRequest;
import io.github.flyinox.coze4j.conversation.message.MessageType;
import io.github.flyinox.coze4j.exception.CozeErrorCode;
import io.github.flyinox.coze4j.exception.CozeException;
import io.github.flyinox.coze4j.file.FileApi;
import io.github.flyinox.coze4j.file.FileResponse;
import io.github.flyinox.coze4j.util.StringUtil;
import io.github.flyinox.coze4j.workspaces.WorkspaceApi;
import io.github.flyinox.coze4j.workspaces.WorkspaceResponse;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CozeClient {
    private static final String DEFAULT_BASE_URL = "https://api.coze.cn/";

    private String apiKey;
    private String baseUrl;
    private Map<String, String> headers;

    ChatApi chatApi;
    AgentApi agentApi;
    ConversationApi conversationApi;
    FileApi fileApi;
    WorkspaceApi workspaceApi;
    AuthApi authApi;


    // getters
    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public CozeClient(String apiKey, String baseUrl, Map<String, String> headers) {
        if (StringUtil.isNullOrEmpty(apiKey)) {
            throw new IllegalArgumentException("apiKey cannot be null or empty");
        }
        if (StringUtil.isNullOrEmpty(baseUrl)) {
            this.baseUrl = DEFAULT_BASE_URL;
        }

        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.headers = headers;
        if (this.headers == null) {
            this.headers = new HashMap<String, String>();
        }
        this.headers.put("Authorization", "Bearer " + apiKey);

        // create services
        this.chatApi = createService(ChatApi.class);
        this.agentApi = createService(AgentApi.class);
        this.conversationApi = createService(ConversationApi.class);
        this.fileApi = createService(FileApi.class);
        this.workspaceApi = createService(WorkspaceApi.class);
        this.authApi = createService(AuthApi.class);
    }

    public CozeClient(String apiKey, String baseUrl) {
        this(apiKey, baseUrl, new HashMap<String, String>());
    }

    // Chat API
    /**
     * Send messages to a published Coze agent with conversation ID
     * @param conversationId The ID of the conversation
     * @param request Chat parameters including messages and settings
     * @return Chat response
     */
    public ChatResponse chat(String conversationId, ChatRequest request) throws CozeException {
        return execute(chatApi.chat(conversationId, request));
    }

    /**
     * Send messages to a published Coze agent
     * @param request Chat parameters including messages and settings
     * @return Chat response
     */
    public ChatResponse chat(ChatRequest request) throws CozeException {
        return execute(chatApi.chat(request));
    }

    /**
     * Callback interface for streaming responses
     */
    public interface StreamCallback {
        /**
         * Called when a chunk of data is received
         * @param chunk The received data chunk
         */
        void onData(String chunk);

        /**
         * Called when an error occurs
         * @param e The exception that occurred
         */
        void onError(Exception e);

        /**
         * Called when the stream is complete
         */
        void onComplete();
    }

    public static class ChatStreamCallback implements Callback<ResponseBody> {
        private StreamCallback callback;

        public ChatStreamCallback(StreamCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                ChatStreamHandler.handleStream(response.body(),
                        new ChatStreamHandler.BaseChatStreamCallback() {
                            @Override
                            public void onChatEvent(ChatEventType eventType, ChatObject chat) {
                                switch (eventType) {
                                    case CHAT_CREATED:
                                        break;
                                    case CHAT_IN_PROGRESS:
                                        break;
                                    case CHAT_COMPLETED:
                                        break;
                                }
                            }

                            @Override
                            public void onMessageEvent(ChatEventType eventType, Message message) {
                                if (eventType == ChatEventType.MESSAGE_DELTA) {
                                    callback.onData(message.getContent());
                                } else if (eventType == ChatEventType.MESSAGE_COMPLETED) {
                                    if (MessageType.ANSWER.getValue().equals(message.getType())) {
                                    }
                                }
                            }

                            @Override
                            public void onError(String errorData) {
                                callback.onError(new CozeException(CozeErrorCode.OTHER_ERROR, errorData));
                            }

                            @Override
                            public void onComplete() {
                                callback.onComplete();
                            }
                        });
            } catch (IOException e) {
                callback.onError(new CozeException(CozeErrorCode.OTHER_ERROR, "Failed to handle stream: " + e.getMessage()));
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            callback.onError(new CozeException(CozeErrorCode.OTHER_ERROR, "Failed to handle stream: " + t.getMessage()));
        }
    }

    public void chatStream(String conversationId, ChatRequest request, StreamCallback callback) throws CozeException {
        chatApi.chatStream(conversationId, request).enqueue(new ChatStreamCallback(callback));
    }
    
    /**
     * Send messages with streaming response
     * @param request Chat parameters with stream=true
     * @param callback Callback to handle streaming responses
     * @return Streaming response body
     */
    public void chatStream(ChatRequest request, StreamCallback callback) throws CozeException {
        chatApi.chatStream(request).enqueue(new ChatStreamCallback(callback));
    }

    /**
     * Get detailed information about a specific chat
     * @param conversationId The ID of the conversation
     * @param chatId The ID of the chat
     * @return Detailed chat information
     */
    public ChatResponse getChatInfo(String conversationId, String chatId) throws CozeException {
        return execute(chatApi.getChatInfo(conversationId, chatId));
    }

    /**
     * Get the list of messages in a chat
     * @param conversationId The ID of the conversation
     * @param chatId The ID of the chat
     * @return List of messages in the chat
     */
    public List<Message> getChatMessages(String conversationId, String chatId) throws CozeException {
        return execute(chatApi.getChatMessages(conversationId, chatId)).getData();
    }

    /**
     * Submit tool execution results
     * @param conversationId The ID of the conversation
     * @param chatId The ID of the chat
     * @param request Tool outputs and stream settings
     * @return Chat object
     */
    public ChatObject submitToolOutputs(String conversationId, String chatId, SubmitToolOutputsRequest request) throws CozeException {
        return execute(chatApi.submitToolOutputs(conversationId, chatId, request)).getData();
    }

    /**
     * Submit tool execution results with streaming response
     * @param conversationId The ID of the conversation
     * @param chatId The ID of the chat
     * @param request Tool outputs with stream=true
     * @return Streaming response
     */
    public void submitToolOutputsStream(String conversationId, String chatId, SubmitToolOutputsRequest request, StreamCallback callback) throws CozeException {
        chatApi.submitToolOutputsStream(conversationId, chatId, request).enqueue(new ChatStreamCallback(callback));
    }

    /**
     * Cancel an ongoing chat
     * @param request Cancel request containing chat and conversation IDs
     * @return Canceled chat information
     */
    public ChatObject cancelChat(ChatCancelRequest request) throws CozeException {
        return execute(chatApi.cancelChat(request)).getData();
    }

    // Conversation API Methods
    /**
     * Create a new conversation
     * @param request The conversation creation parameters
     * @return Response containing the created conversation information
     */
    public ConversationResponse createConversation(ConversationCreateRequest request) throws CozeException {
        return execute(conversationApi.createConversation(request));
    }

    /**
     * Retrieve conversation details
     * @param conversationId Unique identifier of the conversation
     * @return Response containing the conversation information
     */
    public ConversationResponse getConversation(String conversationId) throws CozeException {
        return execute(conversationApi.getConversation(conversationId));
    }

    /**
     * Create a new message in a conversation
     * @param conversationId Unique identifier of the conversation
     * @param request Message creation parameters
     * @return Created message information
     */
    public Message createMessage(String conversationId, MessageCreateRequest request) throws CozeException {
        return execute(conversationApi.createMessage(conversationId, request)).getData();
    }

    /**
     * Retrieve messages from a conversation
     * @param conversationId Unique identifier of the conversation
     * @param request Parameters for filtering and pagination
     * @return List of messages in the conversation
     */
    public MessageListResponse listMessages(String conversationId, MessageListRequest request) throws CozeException {
        return execute(conversationApi.listMessages(conversationId, request));
    }

    /**
     * Retrieve a specific message from a conversation
     * @param conversationId Unique identifier of the conversation
     * @param messageId Unique identifier of the message
     * @return Message details
     */
    public Message getMessage(String conversationId, String messageId) throws CozeException {
        return execute(conversationApi.getMessage(conversationId, messageId)).getData();
    }

    /**
     * Update an existing message in a conversation
     * @param conversationId Unique identifier of the conversation
     * @param messageId Unique identifier of the message
     * @param request Message modification parameters
     * @return Updated message information
     */
    public Message modifyMessage(String conversationId, String messageId, MessageModifyRequest request) throws CozeException {
        return execute(conversationApi.modifyMessage(conversationId, messageId, request)).getData();
    }

    /**
     * Delete a message from a conversation
     * @param conversationId Unique identifier of the conversation
     * @param messageId Unique identifier of the message to delete
     */
    public void deleteMessage(String conversationId, String messageId) throws CozeException {
        execute(conversationApi.deleteMessage(conversationId, messageId));
    }

    // Agent API Methods
    /**
     * Create a new agent
     * @param request The agent creation parameters
     * @return Created agent information
     */
    public AgentCreateResponse createAgent(AgentCreateRequest request) throws CozeException {
        return execute(agentApi.createAgent(request));
    }

    /**
     * Update an existing agent
     * @param request The agent update parameters
     * @return Updated agent information
     */
    public AgentCreateResponse updateAgent(AgentUpdateRequest request) throws CozeException {
        return execute(agentApi.updateAgent(request));
    }

    /**
     * Publish an agent as API service
     * @param request The publish parameters
     * @return Publish result
     */
    public AgentPublishResponse publishAgent(AgentPublishRequest request) throws CozeException {
        return execute(agentApi.publishAgent(request));
    }

    /**
     * Get agent information
     * @param botId The ID of the agent
     * @return Agent configuration information
     */
    public Bot getAgentInfo(String botId) throws CozeException {
        return execute(agentApi.getAgentInfo(botId)).getData();
    }

    /**
     * Get list of published agents
     * @param spaceId The ID of the space
     * @param pageSize Page size (optional)
     * @param pageIndex Page number (optional)
     * @return List of published agents
     */
    public PublishedBotsResponse getPublishedBots(String spaceId, Integer pageSize, Integer pageIndex) throws CozeException {
        return execute(agentApi.getPublishedBots(spaceId, pageSize, pageIndex));
    }

    // Workspace API Methods
    /**
     * List workspaces with pagination
     * @param pageNum Page number (optional)
     * @param pageSize Page size (optional)
     * @return Workspace response containing list of workspaces
     */
    public WorkspaceResponse listWorkspaces(Integer pageNum, Integer pageSize) throws CozeException {
        return execute(workspaceApi.listWorkspaces(pageNum, pageSize));
    }

    /**
     * List workspaces with default pagination
     * @return Workspace response containing list of workspaces
     */
    public WorkspaceResponse listWorkspaces() throws CozeException {
        return execute(workspaceApi.listWorkspaces());
    }

    // File API Methods
    /**
     * Upload a file to Coze platform
     * @param file The file to upload (multipart/form-data)
     * @return Upload result including file ID and information
     */
    public FileResponse uploadFile(MultipartBody.Part file) throws CozeException {
        return execute(fileApi.uploadFile(file));
    }

    /**
     * Get information about a specific file
     * @param fileId The ID of the file to retrieve
     * @return File information including size, name, and creation time
     */
    public FileResponse getFile(String fileId) throws CozeException {
        return execute(fileApi.getFile(fileId));
    }

    // Create a api service for the given class
    public <T> T createService(Class<T> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new GenericHeadersInterceptor(this.headers));
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(serviceClass);
    }

    private <T, R extends CozeBaseResponse<T>> R execute(Call<R> apiCall) throws CozeException {
        try {
            Response<R> response = apiCall.execute();
            if (response.isSuccessful()) {
                R baseResponse = response.body();
                if (baseResponse.code != 0) {
                    throw new CozeException(CozeErrorCode.fromCode(baseResponse.code), baseResponse.msg);
                }
                return baseResponse;
            } else {
                String errorMessage;
                try (okhttp3.ResponseBody errorBody = response.errorBody()) {
                    errorMessage = errorBody != null ? errorBody.string() : "Unknown error";
                }
                throw new CozeException(CozeErrorCode.fromCode(response.code()), errorMessage);
            }
        } catch (IOException e) {
            throw new CozeException(CozeErrorCode.OTHER_ERROR, "Failed to execute API call", e);
        }
    }



    static class GenericHeadersInterceptor implements Interceptor {

        private final Map<String, String> headers = new HashMap<>();

        GenericHeadersInterceptor(Map<String, String> headers) {
            Optional.ofNullable(headers)
                    .ifPresent(this.headers::putAll);
        }

        @NotNull
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            // Add headers
            this.headers.forEach(builder::addHeader);

            return chain.proceed(builder.build());
        }
    }
}
