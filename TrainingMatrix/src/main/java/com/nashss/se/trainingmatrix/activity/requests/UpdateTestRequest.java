package com.nashss.se.trainingmatrix.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateTestRequest.Builder.class)
public class UpdateTestRequest {
    private final String trainingId;
    private final String employeeId;
    private final Integer latestScore;

    private UpdateTestRequest(String trainingId, String employeeId, Integer latestScore) {
        this.trainingId = trainingId;
        this.employeeId = employeeId;
        this.latestScore = latestScore;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Integer getLatestScore() {
        return latestScore;
    }

    @Override
    public String toString() {
        return "UpdateTestRequest{" +
                "trainingId='" + trainingId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", latestScore=" + latestScore +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String trainingId;
        private String employeeId;
        private Integer latestScore;

        public Builder withTrainingId(String trainingId) {
            this.trainingId = trainingId;
            return this;
        }

        public Builder withEmployeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder withLatestScore(Integer latestScore) {
            this.latestScore = latestScore;
            return this;
        }

        public UpdateTestRequest build() {
            return new UpdateTestRequest(trainingId, employeeId, latestScore);
        }
    }
}
