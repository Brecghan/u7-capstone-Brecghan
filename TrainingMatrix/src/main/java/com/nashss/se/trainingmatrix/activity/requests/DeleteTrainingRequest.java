package com.nashss.se.trainingmatrix.activity.requests;

public class DeleteTrainingRequest {
    private final String trainingId;

    private DeleteTrainingRequest(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getTrainingId() {
        return trainingId;
    }

    @Override
    public String toString() {
        return "DeleteTrainingRequest{" +
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

        public DeleteTrainingRequest build() {
            return new DeleteTrainingRequest(trainingId);
        }
    }
}
