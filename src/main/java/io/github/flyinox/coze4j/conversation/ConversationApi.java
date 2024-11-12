package io.github.flyinox.coze4j.conversation;

import io.github.flyinox.coze4j.CozeBaseResponse;
import io.github.flyinox.coze4j.conversation.message.Message;
import io.github.flyinox.coze4j.conversation.message.MessageCreateRequest;
import io.github.flyinox.coze4j.conversation.message.MessageListRequest;
import io.github.flyinox.coze4j.conversation.message.MessageListResponse;
import io.github.flyinox.coze4j.conversation.message.MessageModifyRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * API for conversation operations
 */
public interface ConversationApi {
    /**
     * Create a conversation
     * Requires createConversation permission
     *
     * @param request creation parameters
     * @return created conversation information
     */
    @POST("v1/conversation/create")
    @Headers({"Content-Type: application/json"})
    Call<ConversationResponse> createConversation(
        @Body ConversationCreateRequest request
    );

    /**
     * Get conversation information
     * Requires retrieveConversation permission
     *
     * @param conversationId The ID of the conversation
     * @return conversation information
     */
    @GET("v1/conversation/retrieve")
    @Headers({"Content-Type: application/json"})
    Call<ConversationResponse> getConversation(
        @Query("conversation_id") String conversationId
    );

    /**
     * Create a message in a conversation
     * Requires createMessage permission
     *
     * @param conversationId The ID of the conversation
     * @param request Message creation parameters
     * @return created message information
     */
    @POST("v1/conversation/message/create")
    @Headers({"Content-Type: application/json"})
    Call<CozeBaseResponse<Message>> createMessage(
        @Query("conversation_id") String conversationId,
        @Body MessageCreateRequest request
    );

    /**
     * Get the message list of a conversation
     * Requires listMessage permission
     *
     * @param conversationId The ID of the conversation
     * @param request Message list query parameters
     * @return List of messages in the conversation
     */
    @POST("v1/conversation/message/list")
    @Headers({"Content-Type: application/json"})
    Call<MessageListResponse> listMessages(
        @Query("conversation_id") String conversationId,
        @Body MessageListRequest request
    );

    /**
     * Get detailed information of a specific message
     * Requires retrieveMessage permission
     *
     * @param conversationId The ID of the conversation
     * @param messageId The ID of the message
     * @return Detailed message information
     */
    @GET("v1/conversation/message/retrieve")
    @Headers({"Content-Type: application/json"})
    Call<CozeBaseResponse<Message>> getMessage(
        @Query("conversation_id") String conversationId,
        @Query("message_id") String messageId
    );

    /**
     * Modify a message in a conversation
     * Requires modifyMessage permission
     *
     * @param conversationId The ID of the conversation
     * @param messageId The ID of the message
     * @param request Message modification parameters
     * @return Modified message information
     */
    @POST("v1/conversation/message/modify")
    @Headers({"Content-Type: application/json"})
    Call<CozeBaseResponse<Message>> modifyMessage(
        @Query("conversation_id") String conversationId,
        @Query("message_id") String messageId,
        @Body MessageModifyRequest request
    );

    /**
     * Delete a message from a conversation
     * Requires deleteMessage permission
     * Note:
     * - Batch operations are not supported, messages must be deleted one by one
     * - When deleting an agent reply, all associated intermediate state messages will be automatically deleted
     * - Deleted messages cannot be retrieved through Get conversation or Get chat messages API
     *
     * @param conversationId The ID of the conversation
     * @param messageId The ID of the message to delete
     * @return Empty response on success
     */
    @POST("v1/conversation/message/delete")
    @Headers({"Content-Type: application/json"})
    Call<CozeBaseResponse<Object>> deleteMessage(
        @Query("conversation_id") String conversationId,
        @Query("message_id") String messageId
    );
} 