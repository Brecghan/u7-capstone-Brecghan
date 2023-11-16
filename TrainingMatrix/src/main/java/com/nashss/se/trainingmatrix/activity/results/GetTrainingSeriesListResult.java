package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.TrainingSeriesModel;

import java.util.List;

public class GetTrainingSeriesListResult {
    private final List<TrainingSeriesModel> trainingSeriesList;

    private GetTrainingSeriesListResult(List<TrainingSeriesModel> trainingSeriesList) {
        this.trainingSeriesList = trainingSeriesList;
    }

    public List<TrainingSeriesModel> getTrainingSeriesList() {
        return trainingSeriesList;
    }

    @Override
    public String toString() {
        return "GetTrainingSeriesListResult{" +
                "trainingSeriesList=" + trainingSeriesList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<TrainingSeriesModel> trainingSeriesList ;

        public Builder withTrainingSeriesList(List<TrainingSeriesModel> trainingSeriesList) {
            this.trainingSeriesList = trainingSeriesList;
            return this;
        }

        public GetTrainingSeriesListResult build() {
            return new GetTrainingSeriesListResult(trainingSeriesList);
        }
    }
}
