package com.nashss.se.trainingmatrix.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(builder = CreateTestRequest.Builder.class)
public class CreateTestRequest {
    private final String trainingId;
    private final List<String> employeeIds;
    private final Integer scoreToPass;
    private CreateTestRequest(String trainingId, List<String> employeeIds, Integer scoreToPass) {
        this.trainingId = trainingId;
        this.employeeIds = employeeIds;
        this.scoreToPass = scoreToPass;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public List<String> getEmployeeIds() {
        return employeeIds;
    }

    public Integer getScoreToPass() {
        return scoreToPass;
    }

    @Override
    public String toString() {
        return "CreateTestRequest{" +
                "trainingId='" + trainingId + '\'' +
                ", employeeIds=" + employeeIds +
                ", scoreToPass=" + scoreToPass +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String trainingId;
        private List<String> employeeIds;
        private Integer scoreToPass;

        public Builder withTrainingId(String trainingId) {
            this.trainingId = trainingId;
            return this;
        }

        public Builder withEmployeeIds(List<String> employeeIds) {
            this.employeeIds = new ArrayList<>(employeeIds);
            return this;
        }

        public Builder withScoreToPass(Integer scoreToPass) {
            this.scoreToPass = scoreToPass;
            return this;
        }

        public CreateTestRequest build() {
            return new CreateTestRequest(trainingId, employeeIds, scoreToPass);
        }
    }
}
