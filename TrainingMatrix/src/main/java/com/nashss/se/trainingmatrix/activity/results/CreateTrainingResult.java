package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.TrainingModel;

public class CreateTrainingResult {
    private final TrainingModel training;

    private CreateTrainingResult(TrainingModel training) {
        this.training = training;
    }

    public TrainingModel getTraining() {
        return training;
    }

    @Override
    public String toString() {
        return "CreateTrainingResult{" +
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

        public CreateTrainingResult build() {
            return new CreateTrainingResult(training);
        }
    }
}
