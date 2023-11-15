package com.nashss.se.trainingmatrix.activity.requests;

import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;

public class GetTrainingListRequest {
    private final Status status;

    private final Boolean isActive;

    private final String trainingSeries;

    private GetTrainingListRequest(Boolean isActive, Status status, String trainingSeries) {
        this.isActive = isActive;
        this.status = status;
        this.trainingSeries = trainingSeries;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Status getStatus() {
        return status;
    }

    public String getTrainingSeries() {
        return trainingSeries;
    }

    @Override
    public String toString() {
        return "GetTrainingListRequest{" +
                "status=" + status +
                ", isActive=" + isActive +
                ", trainingSeries='" + trainingSeries + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Status status;

        private Boolean isActive;

        private String trainingSeries;


        public Builder withStatus(String status) {
            if (status.equals("null")) {
                this.status = null;
            } else {
            this.status = Status.valueOf(status); }
            return this;
        }

        public Builder withIsActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder withTrainingSeries(String trainingSeries) {
            if (trainingSeries.equals("null")) {
                this.trainingSeries = null;
            } else {
            this.trainingSeries = trainingSeries; }
            return this;
        }

        public GetTrainingListRequest build() {
            return new GetTrainingListRequest(isActive, status, trainingSeries);
        }
    }
}
