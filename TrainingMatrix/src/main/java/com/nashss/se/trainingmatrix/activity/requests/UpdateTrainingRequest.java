package com.nashss.se.trainingmatrix.activity.requests;

import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Set;

@JsonDeserialize(builder = UpdateTrainingRequest.Builder.class)
public class UpdateTrainingRequest {
    private final String trainingId;
    private final Boolean isActive;
    private final Integer monthsTilExpire;
    private final Set<String> employeesTrained;
    private final Set<String> testsForTraining;
    private final Status expirationStatus;


    private UpdateTrainingRequest(String trainingId, Boolean isActive, Integer monthsTilExpire,
                                  Set<String> employeesTrained, Set<String> testsForTraining, Status expirationStatus) {
        this.trainingId = trainingId;
        this.isActive = isActive;
        this.monthsTilExpire = monthsTilExpire;
        this.employeesTrained = employeesTrained;
        this.testsForTraining = testsForTraining;
        this.expirationStatus = expirationStatus;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public Integer getMonthsTilExpire() {
        return monthsTilExpire;
    }


    public Boolean getIsActive() {
        return isActive;
    }

    public Set<String> getEmployeesTrained() {
        return employeesTrained;
    }

    public Set<String> getTestsForTraining() {
        return testsForTraining;
    }

    public Status getExpirationStatus() {
        return expirationStatus;
    }

    @Override
    public String toString() {
        return "UpdateTrainingRequest{" +
                "trainingId='" + trainingId + '\'' +
                ", isActive=" + isActive +
                ", monthsTilExpire=" + monthsTilExpire +
                ", employeesTrained=" + employeesTrained +
                ", testsForTraining=" + testsForTraining +
                ", expirationStatus=" + expirationStatus +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String trainingId;
        private Boolean isActive;
        private Integer monthsTilExpire;
        private Set<String> employeesTrained;
        private Set<String> testsForTraining;
        private Status expirationStatus;

        public Builder withTrainingId(String trainingId) {
            this.trainingId = trainingId;
            return this;
        }

        public Builder withIsActive(String isActive) {
            this.isActive = Boolean.valueOf(isActive);
            return this;
        }

        public Builder withMonthsTilExpire(Integer monthsTilExpire) {
            this.monthsTilExpire = monthsTilExpire;
            return this;
        }

        public Builder withEmployeesTrained(Set<String> employeesTrained) {
            this.employeesTrained = employeesTrained;
            return this;
        }

        public Builder withTestsForTraining(Set<String> testsForTraining) {
            this.testsForTraining = testsForTraining;
            return this;
        }

        public Builder withExpirationStatus(String expirationStatus) {
            this.expirationStatus = Status.valueOf(expirationStatus);
            return this;
        }

        public UpdateTrainingRequest build() {
            return new UpdateTrainingRequest(trainingId, isActive, monthsTilExpire,
                    employeesTrained, testsForTraining, expirationStatus);
        }
    }
}
