package com.nashss.se.trainingmatrix.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "Tests")
public class Test {
    public static final String TESTS_BY_EMPLOYEE_INDEX = "TestsByEmployeeIndex";
    private String trainingId;
    private String employeeId;
    private Boolean hasPassed;
    private Integer scoreToPass;
    private Integer latestScore;
    private List<String> testAttempts;

    @DynamoDBHashKey(attributeName = "trainingId")
    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexNames = TESTS_BY_EMPLOYEE_INDEX, attributeName = "employeeId")
    @DynamoDBRangeKey(attributeName = "employeeId")
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    @DynamoDBAttribute(attributeName = "hasPassed")
    public Boolean getHasPassed() {
        return hasPassed;
    }

    public void setHasPassed(Boolean hasPassed) {
        this.hasPassed = hasPassed;
    }

    @DynamoDBAttribute(attributeName = "scoreToPass")
    public Integer getScoreToPass() {
        return scoreToPass;
    }

    public void setScoreToPass(Integer scoreToPass) {
        this.scoreToPass = scoreToPass;
    }

    @DynamoDBAttribute(attributeName = "latestScore")
    public Integer getLatestScore() {
        return latestScore;
    }

    public void setLatestScore(Integer latestScore) {
        this.latestScore = latestScore;
    }

    @DynamoDBAttribute(attributeName = "testAttempts")
    public List<String> getTestAttempts() {
        return testAttempts;
    }

    public void setTestAttempts(List<String> testAttempts) {
        this.testAttempts = testAttempts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Test test = (Test) o;
        return Objects.equals(trainingId, test.trainingId) &&
                Objects.equals(employeeId, test.employeeId) &&
                Objects.equals(hasPassed, test.hasPassed) &&
                Objects.equals(scoreToPass, test.scoreToPass) &&
                Objects.equals(latestScore, test.latestScore) &&
                Objects.equals(testAttempts, test.testAttempts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingId, employeeId, hasPassed, scoreToPass, latestScore, testAttempts);
    }

    @Override
    public String toString() {
        return "Test{" +
                "trainingId='" + trainingId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", hasPassed=" + hasPassed +
                ", scoreToPass=" + scoreToPass +
                ", latestScore=" + latestScore +
                ", testAttempts=" + testAttempts +
                '}';
    }
}
