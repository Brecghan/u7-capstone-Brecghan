package com.nashss.se.trainingmatrix.activity.requests;

import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@JsonDeserialize(builder = UpdateEmployeeRequest.Builder.class)
public class UpdateEmployeeRequest {
    private final String employeeName;
    private final String employeeId;
    private final Team team;
    private final Boolean isActive;
    private final Set<String> trainingsTaken;
    private final Set<String> testsTaken;

    private UpdateEmployeeRequest(String employeeName, String employeeId, Team team,
                                  Boolean isActive, Set<String> trainingsTaken, Set<String> testsTaken) {
        this.employeeName = employeeName;
        this.isActive = isActive;
        this.employeeId = employeeId;
        this.team = team;
        this.trainingsTaken = trainingsTaken;
        this.testsTaken = testsTaken;
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
    public Boolean getIsActive() {
        return isActive;
    }
    public Set<String> getTrainingsTaken() {
        return trainingsTaken;
    }
    public Set<String> getTestsTaken() {
        return testsTaken;
    }

    @Override
    public String toString() {
        return "UpdateEmployeeRequest{" +
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
        private Boolean isActive;
        private Set<String> trainingsTaken;
        private Set<String> testsTaken;

        public Builder withEmployeeName(String employeeName) {
            this.employeeName = employeeName;
            return this;
        }

        public Builder withEmployeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder withTeam(String team) {
            this.team = Team.valueOf(team);
            return this;
        }

        public Builder withIsActive(String isActive) {
            this.isActive = Boolean.valueOf(isActive);
            return this;
        }

        public Builder withTrainingsTaken(String trainingsTaken) {
            if (trainingsTaken == null) {
                return null;
            }
            this.trainingsTaken = new HashSet<>(Collections.singleton(trainingsTaken));
            return this;
        }

        public Builder withTestsTaken(String testsTaken) {
            if (testsTaken == null) {
                return null;
            }
            this.testsTaken = new HashSet<>(Collections.singleton(testsTaken));
            return this;
        }

        public UpdateEmployeeRequest build() {
            return new UpdateEmployeeRequest(employeeName, employeeId, team, isActive, trainingsTaken, testsTaken);
        }
    }
}
