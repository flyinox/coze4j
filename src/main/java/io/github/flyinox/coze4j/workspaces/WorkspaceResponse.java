package io.github.flyinox.coze4j.workspaces;

import java.util.List;

import io.github.flyinox.coze4j.CozeBaseResponse;

//Business information of the interface response.
public class WorkspaceResponse extends CozeBaseResponse<WorkspaceResponse.Data> {
    public class Data {
        // The list of workspaces currently joined by the button user.
        private List<OpenSpace> workspaces;
        // The total number of workspaces currently joined by the button user.
        private Long totalCount;

        public Data(List<OpenSpace> workspaces, Long totalCount) {
            this.workspaces = workspaces;
            this.totalCount = totalCount;
        }

        public List<OpenSpace> getWorkspaces() {
            return workspaces;
        }

        public void setWorkspaces(List<OpenSpace> workspaces) {
            this.workspaces = workspaces;
        }

        public Long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Long totalCount) {
            this.totalCount = totalCount;
        }

        @Override
        public String toString() {
            return "OpenSpaceData{" +
                    "workspaces=" + workspaces +
                    ", totalCount=" + totalCount +
                    '}';
        }
    }

    public class OpenSpace {
        // The ID of the workspace.
        private String id;
        // The name of the workspace.
        private String name;
        // The URL address of the space icon.
        private String icon_url;
        // The current user's roles in the space. Enumerated values include:
        // owner: Owner
        // admin: Administrator
        // member: Member
        private String role_type;
        // Space types. Enumeration values include:
        // personal: Personal workspaces
        // team: Team workspaces
        private String workspace_type;

        public OpenSpace(String id, String name, String icon_url, String role_type, String workspace_type) {
            this.id = id;
            this.name = name;
            this.icon_url = icon_url;
            this.role_type = role_type;
            this.workspace_type = workspace_type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public String getRole_type() {
            return role_type;
        }

        public void setRole_type(String role_type) {
            this.role_type = role_type;
        }

        public String getWorkspace_type() {
            return workspace_type;
        }

        public void setWorkspace_type(String workspace_type) {
            this.workspace_type = workspace_type;
        }

        @Override
        public String toString() {
            return "OpenSpace{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", icon_url='" + icon_url + '\'' +
                    ", role_type='" + role_type + '\'' +
                    ", workspace_type='" + workspace_type + '\'' +
                    '}';
        }
    }
}
