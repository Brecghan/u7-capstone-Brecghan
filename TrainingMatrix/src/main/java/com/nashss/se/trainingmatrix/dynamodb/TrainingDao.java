package com.nashss.se.trainingmatrix.dynamodb;

import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.exceptions.TrainingNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * Returns the {@link Training} corresponding to the specified id.
     *
     * @param trainingId the training ID
     * @return the stored Training, or null if none was found.
     */
    public Training getTraining(String trainingId) {
        Training training = this.dynamoDbMapper.load(Training.class, trainingId);

        if (training == null) {
            throw new TrainingNotFoundException("Could not find training with id " + trainingId);
        }
        return training;
    }

    /**
     * Saves (creates or updates) the given Training.
     *
     * @param training The Training to save
     * @return The Training object that was saved
     */
    public Training saveTraining(Training training) {
        this.dynamoDbMapper.save(training);
        return training;
    }
    /**
     * Perform a search (via a "scan") of the Training table for trainings matching the given criteria.
     * <p>
     * The criteria options are isActive, in which only trainings that match the status passed will
     * be returned, and Team, in which only trainings that match the team passed will be returned.
     * If no criteria is specified, all trainings will be returned
     *
     * @param isActive a Boolean containing the training active status requested or null.
     * @param status an Enum containing a training's current expiration status.
     * @param trainingSeries a string containing the reoccurring trainingSeries a training belongs to.
     * @return a List of Training objects that match the search criteria.
     */
    public List<Training> getTrainingList(Boolean isActive, Status status, String trainingSeries) {
        if (!isActive && status == null && trainingSeries == null) {
            DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();

            return this.dynamoDbMapper.scan(Training.class, dynamoDBScanExpression);

        } else if (isActive && status == null && trainingSeries == null) {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":isActive", new AttributeValue().withBOOL(isActive));
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression("isActive = :isActive")
                    .withExpressionAttributeValues(valueMap);

            return dynamoDbMapper.scan(Training.class, scanExpression);

        } else if (isActive && status != null && trainingSeries == null) {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":isActive", new AttributeValue().withBOOL(isActive));
            valueMap.put(":status", new AttributeValue().withS(String.valueOf(status)));
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression("isActive = :isActive AND expirationStatus = :status")
                    .withExpressionAttributeValues(valueMap);

            return dynamoDbMapper.scan(Training.class, scanExpression);

        } else {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":trainingSeries", new AttributeValue().withS(trainingSeries));
            valueMap.put(":isActive", new AttributeValue().withBOOL(isActive));
            DynamoDBQueryExpression<Training> queryExpression = new DynamoDBQueryExpression<Training>()
                    .withIndexName(Training.TRAININGS_BY_SERIES_INDEX)
                    .withConsistentRead(false)
                    .withKeyConditionExpression("trainingSeries = :trainingSeries")
                    .withFilterExpression("isActive = :isActive")
                    .withExpressionAttributeValues(valueMap);

            return dynamoDbMapper.query(Training.class, queryExpression);
        }
    }
}
