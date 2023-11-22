package com.nashss.se.trainingmatrix.models;

import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EmployeeModel {
    private final String employeeId;
    private final String employeeName;
    private final Boolean isActive;
    private final Team team;
    private final String startDate;
    private final Set<String> trainingsTaken;
    private final Set<String> testsTaken;
    private final Status trainingStatus;
    private EmployeeModel(String employeeId, String employeeName, Boolean isActive, Team team, String startDate,
                          Set<String> trainingsTaken, Set<String> testsTaken, Status trainingStatus) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.isActive = isActive;
        this.team = team;
        this.startDate = startDate;
        this.trainingsTaken = trainingsTaken;
        this.testsTaken = testsTaken;
        this.trainingStatus = trainingStatus;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Team getTeam() {
        return team;
    }

    public String getStartDate() {
        return startDate;
    }

    public Set<String> getTrainingsTaken() {
        return new HashSet<>(trainingsTaken);
    }

    public Set<String> getTestsTaken() {
        return new HashSet<>(testsTaken);
    }

    public Status getTrainingStatus() {
        return trainingStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeModel that = (EmployeeModel) o;
        return employeeId.equals(that.employeeId) &&
                employeeName.equals(that.employeeName) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(team, that.team) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(trainingsTaken, that.trainingsTaken) &&
                Objects.equals(testsTaken, that.testsTaken) &&
                Objects.equals(trainingStatus, that.trainingStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, employeeName, isActive, team, startDate,
                trainingsTaken, testsTaken, trainingStatus);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String employeeId;
        private String employeeName;
        private Boolean isActive;
        private Team team;
        private String startDate;
        private Set<String> trainingsTaken;
        private Set<String> testsTaken;
        private Status trainingStatus;

        public Builder withEmployeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder withEmployeeName(String employeeName) {
            this.employeeName = employeeName;
            return this;
        }

        public Builder withIsActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder withTeam(Team team) {
            this.team = team;
            return this;
        }

        public Builder withStartDate(ZonedDateTime startDate) {
            this.startDate = startDate.toString();
            return this;
        }

        public Builder withTrainingsTaken(Set<String> trainingsTaken) {
            if (null == trainingsTaken) {
                this.trainingsTaken = null;
            } else {
                this.trainingsTaken = new HashSet<>(trainingsTaken);
            }
            return this;
        }

        public Builder withTestsTaken(Set<String> testsTaken) {
            if (null == testsTaken) {
                this.testsTaken = null;
            } else {
                this.testsTaken = new HashSet<>(testsTaken);
            }
            return this;
        }

        public Builder withTrainingStatus(Status trainingStatus) {
            this.trainingStatus = trainingStatus;
            return this;
        }

        public EmployeeModel build() {
            return new EmployeeModel(employeeId, employeeName, isActive, team, startDate,
                    trainingsTaken, testsTaken, trainingStatus);
        }
    }
}
