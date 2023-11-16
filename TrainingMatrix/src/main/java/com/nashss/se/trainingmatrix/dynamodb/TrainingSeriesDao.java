package com.nashss.se.trainingmatrix.dynamodb;

import com.nashss.se.trainingmatrix.dynamodb.models.TrainingSeries;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import java.util.List;
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

    /**
     * Saves (creates) the given TrainingSeries.
     *
     * @param trainingSeries The TrainingSeries to save
     * @return The TrainingSeries object that was saved
     */
    public TrainingSeries saveTrainingSeries(TrainingSeries trainingSeries) {
        this.dynamoDbMapper.save(trainingSeries);
        return trainingSeries;
    }
    /**
     * Returns all TrainingSeries from the TrainingSeries table.
     * <p>
     *
     * @return a List of TrainingSeries objects.
     */
    public List<TrainingSeries> getTrainingSeriesList() {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
        return this.dynamoDbMapper.scan(TrainingSeries.class, dynamoDBScanExpression);
    }
}
