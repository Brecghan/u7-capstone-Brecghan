package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.TrainingModel;

public class GetTrainingResult {
    private final TrainingModel training;

    private GetTrainingResult(TrainingModel training) {
        this.training = training;
    }

    public TrainingModel getTraining() {
        return training;
    }

    @Override
    public String toString() {
        return "GetTrainingResult{" +
                "training=" + training +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private TrainingModel training ;

        public Builder withTraining(TrainingModel training) {
            this.training = training;
            return this;
        }

        public GetTrainingResult build() {
            return new GetTrainingResult(training);
        }
    }
}
