package com.nashss.se.trainingmatrix.activity.requests;

import com.nashss.se.trainingmatrix.utils.NameConverter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateTrainingRequest.Builder.class)
public class CreateTrainingRequest {
    private final String trainingName;
    private final String trainingSeries;
    private final String trainingDate;
    private final Integer monthsTilExpire;
    private static final NameConverter converter = new NameConverter();

    private CreateTrainingRequest(String trainingName, String trainingSeries, Integer monthsTilExpire, String trainingDate) {
        this.trainingName = trainingName;
        this.trainingSeries = trainingSeries;
        this.monthsTilExpire = monthsTilExpire;
        this.trainingDate = trainingDate;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public String getTrainingSeries() {
        return trainingSeries;
    }

    public Integer getMonthsTilExpire() {
        return monthsTilExpire;
    }

    public String getTrainingDate() {
        return trainingDate;
    }

    @Override
    public String toString() {
        return "CreateTrainingRequest{" +
                "trainingName='" + trainingName + '\'' +
                ", trainingSeries='" + trainingSeries + '\'' +
                ", trainingDate='" + trainingDate + '\'' +
                ", monthsTilExpire=" + monthsTilExpire +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String trainingName;
        private String trainingSeries;
        private String trainingDate;
        private Integer monthsTilExpire;

        public Builder withTrainingName(String trainingName) {
            this.trainingName = converter.nameConvert(trainingName);
            return this;
        }

        public Builder withTrainingSeries(String trainingSeries) {
            this.trainingSeries = converter.nameConvert(trainingSeries);
            return this;
        }

        public Builder withTrainingDate(String trainingDate) {
            this.trainingDate = trainingDate;
            return this;
        }

        public Builder withMonthsTilExpire(Integer monthsTilExpire) {
            this.monthsTilExpire = monthsTilExpire;
            return this;
        }

        public CreateTrainingRequest build() {
            return new CreateTrainingRequest(trainingName, trainingSeries, monthsTilExpire, trainingDate);
        }
    }
}
