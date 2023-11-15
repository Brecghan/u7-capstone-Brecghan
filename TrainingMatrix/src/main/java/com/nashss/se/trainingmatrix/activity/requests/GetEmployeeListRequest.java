package com.nashss.se.trainingmatrix.activity.requests;

import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

public class GetEmployeeListRequest {
    private final Team team;

    private final Boolean isActive;

    private GetEmployeeListRequest(Boolean isActive, Team team) {
        this.isActive = isActive;
        this.team = team;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return "GetEmployeeListRequest{" +
                "isActive='" + isActive + '\'' +
                "team='" + team + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Team team;

        private Boolean isActive;


        public Builder withTeam(String team) {
            if (team.equals("null")) {
                this.team = null;
            } else {
            this.team = Team.valueOf(team); }
            return this;
        }

        public Builder withIsActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public GetEmployeeListRequest build() {
            return new GetEmployeeListRequest(isActive, team);
        }
    }
}
