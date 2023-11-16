package com.nashss.se.trainingmatrix.activity.requests;

import com.nashss.se.trainingmatrix.utils.NameConverter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateTrainingSeriesRequest.Builder.class)
public class CreateTrainingSeriesRequest {

    private static final NameConverter converter = new NameConverter();
    private final String trainingSeriesName;

    private CreateTrainingSeriesRequest(String trainingSeriesName) {
        this.trainingSeriesName = trainingSeriesName;
    }

    public String getTrainingSeriesName() {
        return trainingSeriesName;
    }

    @Override
    public String toString() {
        return "CreateTrainingSeriesRequest{" +
                "trainingSeriesName='" + trainingSeriesName + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String trainingSeriesName;


        public Builder withTrainingSeriesName(String trainingSeriesName) {
            this.trainingSeriesName = converter.nameConvert(trainingSeriesName);
            return this;
        }

        public CreateTrainingSeriesRequest build() {
            return new CreateTrainingSeriesRequest(trainingSeriesName);
        }
    }
}
