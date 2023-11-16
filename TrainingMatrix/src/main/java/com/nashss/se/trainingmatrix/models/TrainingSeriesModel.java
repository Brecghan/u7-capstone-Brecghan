package com.nashss.se.trainingmatrix.models;

import java.util.Objects;

public class TrainingSeriesModel {
    private final String trainingSeriesName;

    private TrainingSeriesModel(String trainingSeriesName) {
        this.trainingSeriesName = trainingSeriesName;
    }

    public String getTrainingSeriesName() {
        return trainingSeriesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrainingSeriesModel that = (TrainingSeriesModel) o;
        return Objects.equals(trainingSeriesName, that.trainingSeriesName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingSeriesName);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String trainingSeriesName;

        public Builder withTrainingSeriesName(String trainingSeriesName) {
            this.trainingSeriesName = trainingSeriesName;
            return this;
        }

        public TrainingSeriesModel build() {
            return new TrainingSeriesModel(trainingSeriesName);
        }
    }
}
