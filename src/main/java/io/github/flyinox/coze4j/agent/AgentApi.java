package io.github.flyinox.coze4j.agent;

import io.github.flyinox.coze4j.CozeBaseResponse;
import io.github.flyinox.coze4j.agent.model.Bot;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AgentApi {
    /**
     * Create a new agent
     * Requires createBot permission
     *
     * @param request creation parameters
     * @return created agent information
     */
    @POST("v1/bot/create")
    @Headers({"Content-Type: application/json"})
    Call<AgentCreateResponse> createAgent(@Body AgentCreateRequest request);

    /**
     * Update agent configuration
     * Requires edit permission
     *
     * @param request update parameters
     * @return updated agent information
     */
    @POST("v1/bot/update")
    @Headers({"Content-Type: application/json"})
    Call<AgentCreateResponse> updateAgent(@Body AgentUpdateRequest request);

    /**
     * Publish agent as API service
     * Requires publish permission
     *
     * @param request publish parameters
     * @return publish result
     */
    @POST("v1/bot/publish")
    @Headers({"Content-Type: application/json"})
    Call<AgentPublishResponse> publishAgent(@Body AgentPublishRequest request);

    /**
     * Get agent configuration information
     * Requires getMetadata permission
     * Note: The agent must be published as API service first
     *
     * @param botId The ID of the agent
     * @return agent configuration information
     */
    @GET("v1/bot/get_online_info")
    @Headers({"Content-Type: application/json"})
    Call<CozeBaseResponse<Bot>> getAgentInfo(@Query("bot_id") String botId);

    /**
     * Get the list of agents published as API service
     * Requires getPublishedBot permission
     *
     * @param spaceId The ID of the space
     * @param pageSize Pagination size, default is 20
     * @param pageIndex Page number, default is 1
     * @return list of published agents
     */
    @GET("v1/space/published_bots_list")
    @Headers({"Content-Type: application/json"})
    Call<PublishedBotsResponse> getPublishedBots(
        @Query("space_id") String spaceId,
        @Query("page_size") Integer pageSize,
        @Query("page_index") Integer pageIndex
    );
}