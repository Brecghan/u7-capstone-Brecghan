package com.nashss.se.trainingmatrix.activity.requests;

import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateEmployeeRequest.Builder.class)
public class CreateEmployeeRequest {
    private final String employeeName;
    private final String employeeId;
    private final Team team;
    private final String startDate;

    private CreateEmployeeRequest(String employeeName, String employeeId, Team team, String startDate) {
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.team = team;
        this.startDate = startDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Team getTeam() {
        return team;
    }

    public String getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return "CreateEmployeeRequest{" +
                "employeeName='" + employeeName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String employeeName;
        private String employeeId;
        private Team team;
        private String startDate;

        public Builder withEmployeeName(String employeeName) {
            this.employeeName = employeeName;
            return this;
        }

        public Builder withEmployeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder withTeam(Team team) {
            this.team = team;
            return this;
        }

        public Builder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public CreateEmployeeRequest build() {
            return new CreateEmployeeRequest(employeeName, employeeId, team, startDate);
        }
    }
}
