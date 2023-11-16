package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.TrainingSeriesModel;

public class CreateTrainingSeriesResult {
    private final TrainingSeriesModel trainingSeries;

    private CreateTrainingSeriesResult(TrainingSeriesModel trainingSeries) {
        this.trainingSeries = trainingSeries;
    }

    public TrainingSeriesModel getTrainingSeries() {
        return trainingSeries;
    }

    @Override
    public String toString() {
        return "CreateTrainingSeriesResult{" +
                "trainingSeries=" + trainingSeries +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private TrainingSeriesModel trainingSeries;

        public Builder withTrainingSeries(TrainingSeriesModel trainingSeries) {
            this.trainingSeries = trainingSeries;
            return this;
        }

        public CreateTrainingSeriesResult build() {
            return new CreateTrainingSeriesResult(trainingSeries);
        }
    }
}
