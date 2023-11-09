package com.nashss.se.trainingmatrix.dynamodb;

import com.nashss.se.trainingmatrix.dynamodb.models.Training;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a Training using {@link Training} to represent the model in DynamoDB.
 */
@Singleton
public class TrainingDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a TrainingDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the trainings table
     */
    @Inject
    public TrainingDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }
}
