package com.nashss.se.trainingmatrix.dynamodb;

import com.nashss.se.trainingmatrix.dynamodb.models.Test;
import com.nashss.se.trainingmatrix.exceptions.TestNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Returns the {@link Test} corresponding to the specified IDs.
     *
     * @param trainingId the training ID
     * @param employeeId the employee ID
     * @return the stored Test, or null if none was found.
     */
    public Test getTest(String trainingId, String employeeId) {
        Test test = this.dynamoDbMapper.load(Test.class, trainingId, employeeId);

        if (test == null) {
            throw new TestNotFoundException("Could not find test with IDs " + trainingId + employeeId);
        }
        return test;
    }

    /**
     * Saves (creates or updates) the given Test.
     *
     * @param test The Test to save
     * @return The Test object that was saved
     */
    public Test saveTest(Test test) {
        this.dynamoDbMapper.save(test);
        return test;
    }
    /**
     * Perform a search (via a "scan") of the Test table for tests matching the given criteria.
     * <p>
     * The criteria options are hasPassed, in which only tests that have been passed will
     * be returned, trainingId, in which only tests that match the training will be returned,
     * and employeeId, in which only tests that match the employee will be returned
     * If no criteria is specified, all tests will be returned
     *
     * @param hasPassed a Boolean containing the test status requested or null.
     * @param trainingId the training ID
     * @param employeeId the employee ID
     * @return a List of Test objects that match the search criteria.
     */
    public List<Test> getTestList(Boolean hasPassed, String trainingId, String employeeId) {
        if (!hasPassed && trainingId == null && employeeId == null) {
            DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();

            return this.dynamoDbMapper.scan(Test.class, dynamoDBScanExpression);

        } else if (hasPassed && trainingId == null && employeeId == null) {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":hasPassed", new AttributeValue().withBOOL(hasPassed));
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression("hasPassed = :hasPassed")
                    .withExpressionAttributeValues(valueMap);

            return dynamoDbMapper.scan(Test.class, scanExpression);

        } else if (hasPassed && trainingId != null && employeeId == null) {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":hasPassed", new AttributeValue().withBOOL(hasPassed));
            valueMap.put(":trainingId", new AttributeValue().withS(String.valueOf(trainingId)));
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression("hasPassed = :hasPassed AND trainingId = :trainingId")
                    .withExpressionAttributeValues(valueMap);

            return dynamoDbMapper.scan(Test.class, scanExpression);

        } else {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":employeeId", new AttributeValue().withS(employeeId));
            valueMap.put(":hasPassed", new AttributeValue().withBOOL(hasPassed));
            DynamoDBQueryExpression<Test> queryExpression = new DynamoDBQueryExpression<Test>()
                    .withIndexName(Test.TESTS_BY_EMPLOYEE_INDEX)
                    .withConsistentRead(false)
                    .withKeyConditionExpression("employeeId = :employeeId")
                    .withFilterExpression("hasPassed = :hasPassed")
                    .withExpressionAttributeValues(valueMap);

            return dynamoDbMapper.query(Test.class, queryExpression);
        }
    }
}
