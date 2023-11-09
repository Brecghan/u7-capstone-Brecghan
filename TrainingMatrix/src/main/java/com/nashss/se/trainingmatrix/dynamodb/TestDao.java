package com.nashss.se.trainingmatrix.dynamodb;

import com.nashss.se.trainingmatrix.dynamodb.models.Test;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a Test using {@link Test} to represent the model in DynamoDB.
 */
@Singleton
public class TestDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a TestDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the tests table
     */
    @Inject
    public TestDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

}
