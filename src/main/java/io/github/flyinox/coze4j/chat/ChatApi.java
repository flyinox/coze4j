package io.github.flyinox.coze4j.chat;

import java.util.List;

import io.github.flyinox.coze4j.CozeBaseResponse;
import io.github.flyinox.coze4j.chat.model.ChatCancelRequest;
import io.github.flyinox.coze4j.chat.model.ChatObject;
import io.github.flyinox.coze4j.conversation.message.Message;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * API for chat operations
 */
public interface ChatApi {
    /**
     * Send messages to a published Coze agent
     * Supports both streaming and non-streaming responses
     *
     * @param conversationId The ID of the conversation
     * @param request Chat parameters including messages and settings
     * @return Chat response or streaming response body
     */
    @POST("v3/chat")
    @Headers({"Content-Type: application/json"})
    Call<ChatResponse> chat(@Query("conversation_id") String conversationId, @Body ChatRequest request);

    /**
     * Send messages to a published Coze agent
     *
     * @param request Chat parameters including messages and settings
     * @return Chat response or streaming response body
     */
    @POST("v3/chat")
    @Headers({"Content-Type: application/json"})
    Call<ChatResponse> chat(@Body ChatRequest request);

    /**
     * Send messages with streaming response
     *
     * @param conversationId The ID of the conversation
     * @param request Chat parameters with stream=true
     * @return Streaming response body
     */
    @POST("v3/chat")
    @Headers({"Content-Type: application/json"})
    @Streaming
    Call<ResponseBody> chatStream(@Query("conversation_id") String conversationId, @Body ChatRequest request);

    /**
     * Send messages with streaming response
     *
     * @param request Chat parameters with stream=true
     * @return Streaming response body
     */
    @POST("v3/chat")
    @Headers({"Content-Type: application/json"})
    @Streaming
    Call<ResponseBody> chatStream(@Body ChatRequest request);

    /**
     * Get detailed information about a specific chat
     * Requires getChat permission
     * Note: Only available for chats with auto_save_history=true
     *
     * @param conversationId The ID of the conversation
     * @param chatId The ID of the chat
     * @return Detailed chat information
     */
    @GET("v3/chat/retrieve")
    @Headers({"Content-Type: application/json"})
    Call<ChatResponse> getChatInfo(
        @Query("conversation_id") String conversationId,
        @Query("chat_id") String chatId
    );

    /**
     * Get the list of messages in a chat
     * Requires chat permission
     * Note: It's recommended to check chat status is 'completed' before calling this API
     *
     * @param conversationId The ID of the conversation
     * @param chatId The ID of the chat
     * @return List of messages in the chat
     */
    @GET("v3/chat/message/list")
    @Headers({"Content-Type: application/json"})
    Call<CozeBaseResponse<List<Message>>> getChatMessages(
        @Query("conversation_id") String conversationId,
        @Query("chat_id") String chatId
    );

    /**
     * Submit tool execution results
     * Requires submitToolChat permission
     *
     * @param conversationId The ID of the conversation
     * @param chatId The ID of the chat
     * @param request Tool outputs and stream settings
     * @return Chat response or streaming response
     */
    @POST("v3/chat/submit_tool_outputs")
    @Headers({"Content-Type: application/json"})
    Call<CozeBaseResponse<ChatObject>> submitToolOutputs(
        @Query("conversation_id") String conversationId,
        @Query("chat_id") String chatId,
        @Body SubmitToolOutputsRequest request
    );

    /**
     * Submit tool execution results with streaming response
     *
     * @param conversationId The ID of the conversation
     * @param chatId The ID of the chat
     * @param request Tool outputs with stream=true
     * @return Streaming response
     */
    @POST("v3/chat/submit_tool_outputs")
    @Headers({"Content-Type: application/json"})
    @Streaming
    Call<ResponseBody> submitToolOutputsStream(
        @Query("conversation_id") String conversationId,
        @Query("chat_id") String chatId,
        @Body SubmitToolOutputsRequest request
    );

    /**
     * Cancel an ongoing chat
     * Requires chat permission
     * Note:
     * - Only chats in 'created' or 'in_progress' status can be canceled
     * - After cancellation, the chat will be in 'canceled' status
     * - The response will not be interrupted immediately
     * - If autoSaveHistory=true, messages can still be retrieved
     *
     * @param request Cancel request containing chat and conversation IDs
     * @return Canceled chat information
     */
    @POST("v3/chat/cancel")
    @Headers({"Content-Type: application/json"})
    Call<CozeBaseResponse<ChatObject>> cancelChat(@Body ChatCancelRequest request);
} 