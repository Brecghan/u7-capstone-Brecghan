package com.nashss.se.trainingmatrix.dynamodb;

import com.nashss.se.trainingmatrix.dynamodb.models.TrainingSeries;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a TrainingSeries using {@link TrainingSeries} to represent the model in DynamoDB.
 */
@Singleton
public class TrainingSeriesDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a TrainingSeriesDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the Training Series table
     */
    @Inject
    public TrainingSeriesDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }
}
