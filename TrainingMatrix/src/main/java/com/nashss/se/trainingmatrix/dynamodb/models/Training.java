package com.nashss.se.trainingmatrix.dynamodb.models;

import com.nashss.se.trainingmatrix.converters.DateConverter;
import com.nashss.se.trainingmatrix.converters.SetConverter;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@DynamoDBTable(tableName = "Trainings")
public class Training {
    public static final String TRAININGS_BY_SERIES_INDEX = "TrainingsBySeriesIndex";
    private String trainingId;
    private String trainingName;
    private Boolean isActive;
    private Integer monthsTilExpire;
    private ZonedDateTime trainingDate;
    private Set<String> employeesTrained;
    private Set<String> testsForTraining;
    private Status expirationStatus;
    private String trainingSeries;

    @DynamoDBHashKey(attributeName = "trainingId")
    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    @DynamoDBAttribute(attributeName = "trainingName")
    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    @DynamoDBAttribute(attributeName = "isActive")
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @DynamoDBAttribute(attributeName = "monthsTilExpire")
    public Integer getMonthsTilExpire() {
        return monthsTilExpire;
    }

    public void setMonthsTilExpire(Integer monthsTilExpire) {
        this.monthsTilExpire = monthsTilExpire;
    }

    @DynamoDBTypeConverted(converter = DateConverter.class)
    @DynamoDBAttribute(attributeName = "trainingDate")
    public ZonedDateTime getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(ZonedDateTime trainingDate) {
        this.trainingDate = trainingDate;
    }

    /**
     * Returns the set of Tests associated with this Training, null if there are none.
     *
     * @return Set of Tests for this Training
     */
    @DynamoDBTypeConverted(converter = SetConverter.class)
    @DynamoDBAttribute(attributeName = "testsForTraining")
    public Set<String> getTestsForTraining() {
        // normally, we would prefer to return an empty Set if there is no
        // inventory, but DynamoDB doesn't represent empty Sets...needs to be null
        // instead
        if (null == testsForTraining) {
            return null;
        }
        return new HashSet<>(testsForTraining);
    }

    /**
     * Sets the Tests for this Training as a copy of input, or null if input is null.
     *
     * @param testsForTraining Set of Tests for this Training
     */
    public void setTestsForTraining(Set<String> testsForTraining) {
        // see comment in getEmployeesTrained()
        if (null == testsForTraining) {
            this.testsForTraining = null;
        } else {
            this.testsForTraining = new HashSet<>(testsForTraining);
        }
    }

    /**
     * Returns the set of Employees associated with this Training, null if there are none.
     *
     * @return Set of Employees for this Training
     */
    @DynamoDBTypeConverted(converter = SetConverter.class)
    @DynamoDBAttribute(attributeName = "employeesTrained")
    public Set<String> getEmployeesTrained() {
        // normally, we would prefer to return an empty Set if there is no
        // inventory, but DynamoDB doesn't represent empty Sets...needs to be null
        // instead
        if (null == employeesTrained) {
            return null;
        }
        return new HashSet<>(employeesTrained);
    }

    /**
     * Sets the Employees for this Training as a copy of input, or null if input is null.
     *
     * @param employeesTrained Set of Employees for this Training
     */
    public void setEmployeesTrained(Set<String> employeesTrained) {
        // see comment in getEmployeesTrained()
        if (null == employeesTrained) {
            this.employeesTrained = null;
        } else {
            this.employeesTrained = new HashSet<>(employeesTrained);
        }
    }

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "expirationStatus")
    public Status getExpirationStatus() {
        return expirationStatus;
    }

    public void setExpirationStatus(Status expirationStatus) {
        this.expirationStatus = expirationStatus;
    }
    @DynamoDBIndexHashKey(globalSecondaryIndexNames = TRAININGS_BY_SERIES_INDEX, attributeName = "trainingSeries")
    public String getTrainingSeries() {
        return trainingSeries;
    }

    public void setTrainingSeries(String trainingSeries) {
        this.trainingSeries = trainingSeries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Training that = (Training) o;
        return trainingId.equals(that.trainingId) &&
                trainingName.equals(that.trainingName) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(monthsTilExpire, that.monthsTilExpire) &&
                Objects.equals(trainingDate, that.trainingDate) &&
                Objects.equals(employeesTrained, that.employeesTrained) &&
                Objects.equals(testsForTraining, that.testsForTraining) &&
                Objects.equals(trainingSeries, that.trainingSeries) &&
                Objects.equals(expirationStatus, that.expirationStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingId, trainingName, isActive, monthsTilExpire, trainingDate,
                employeesTrained, testsForTraining, trainingSeries, expirationStatus);
    }

    @Override
    public String toString() {
        return "Training{" +
                "trainingId='" + trainingId + '\'' +
                ", trainingName=" + trainingName +
                ", monthsTilExpire='" + monthsTilExpire + '\'' +
                ", trainingDate='" + trainingDate + '\'' +
                '}';
    }
}