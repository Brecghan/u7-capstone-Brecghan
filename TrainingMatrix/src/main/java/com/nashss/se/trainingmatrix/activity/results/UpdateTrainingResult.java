package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.TrainingModel;

public class UpdateTrainingResult {
    private final TrainingModel training;

    private UpdateTrainingResult(TrainingModel training) {
        this.training = training;
    }

    public TrainingModel getTraining() {
        return training;
    }

    @Override
    public String toString() {
        return "UpdateTrainingResult{" +
                "training=" + training +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private TrainingModel training;

        public Builder withTraining(TrainingModel training) {
            this.training = training;
            return this;
        }

        public UpdateTrainingResult build() {
            return new UpdateTrainingResult(training);
        }
    }
}
