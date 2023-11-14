package com.nashss.se.trainingmatrix.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "TrainingSeries")
public class TrainingSeries {
    private String trainingSeriesName;

    @DynamoDBHashKey(attributeName = "trainingId")
    public String getTrainingSeriesName() {
        return trainingSeriesName;
    }

    public void setTrainingSeriesName(String trainingSeriesName) {
        this.trainingSeriesName = trainingSeriesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingSeries that = (TrainingSeries) o;
        return Objects.equals(trainingSeriesName, that.trainingSeriesName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingSeriesName);
    }

    @Override
    public String toString() {
        return "TrainingSeries{" +
                "trainingSeriesName='" + trainingSeriesName + '\'' +
                '}';
    }
}
