package com.nashss.se.trainingmatrix.activity.requests;

public class GetTrainingRequest {
    private final String trainingId;

    private GetTrainingRequest(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getTrainingId() {
        return trainingId;
    }

    @Override
    public String toString() {
        return "GetTrainingRequest{" +
                "trainingId='" + trainingId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String trainingId;

        public Builder withTrainingId(String trainingId) {
            this.trainingId = trainingId;
            return this;
        }

        public GetTrainingRequest build() {
            return new GetTrainingRequest(trainingId);
        }
    }
}
