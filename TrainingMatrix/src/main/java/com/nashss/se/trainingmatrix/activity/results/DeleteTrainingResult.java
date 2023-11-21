package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.TrainingModel;

public class DeleteTrainingResult {
    private final TrainingModel training;

    private DeleteTrainingResult(TrainingModel training) {
        this.training = training;
    }

    public TrainingModel getTraining() {
        return training;
    }

    @Override
    public String toString() {
        return "DeleteTrainingResult{" +
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

        public DeleteTrainingResult build() {
            return new DeleteTrainingResult(training);
        }
    }
}
