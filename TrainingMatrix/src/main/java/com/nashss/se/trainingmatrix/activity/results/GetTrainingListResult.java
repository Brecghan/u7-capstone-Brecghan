package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.TrainingModel;

import java.util.List;

public class GetTrainingListResult {
    private final List<TrainingModel> trainings;

    private GetTrainingListResult(List<TrainingModel> trainings) {
        this.trainings = trainings;
    }

    public List<TrainingModel> getTrainings() {
        return trainings;
    }

    @Override
    public String toString() {
        return "GetTrainingListResult{" +
                "trainings=" + trainings +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<TrainingModel> trainings ;

        public Builder withTrainings(List<TrainingModel> trainings) {
            this.trainings = trainings;
            return this;
        }

        public GetTrainingListResult build() {
            return new GetTrainingListResult(trainings);
        }
    }
}
