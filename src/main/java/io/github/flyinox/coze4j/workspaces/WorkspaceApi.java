package io.github.flyinox.coze4j.workspaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

// View the list of workspaces the current user has joined.
// The list of workspaces includes:
//  Personal and team workspaces created by the current user.
//  Team workspaces the current user has joined as a team member.
// more details you can find here: https://www.coze.com/docs/developer_guides/b1t2kfxr
public interface WorkspaceApi {
    //page_num Integer Optional Page number. The default is 1, which means data is returned starting from the first page.
    //page_size Integer Optional Page size. Default is 20, maximum is 50.
   @GET("v1/workspaces") 
   @Headers({"Content-Type: application/json"})
   Call<WorkspaceResponse> listWorkspaces(@Query("page_num") Integer page_num, @Query("page_size") Integer page_size);

   @GET("v1/workspaces") 
   @Headers({"Content-Type: application/json"})
   Call<WorkspaceResponse> listWorkspaces();
}
