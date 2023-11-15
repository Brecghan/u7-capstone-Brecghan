package com.nashss.se.trainingmatrix.dynamodb.models;

import com.nashss.se.trainingmatrix.converters.DateConverter;
import com.nashss.se.trainingmatrix.converters.SetConverter;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

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

@DynamoDBTable(tableName = "Employees")
public class Employee {
    public static final String EMPLOYEES_BY_TEAM_INDEX = "EmployeesByTeamIndex";
    private String employeeId;
    private String employeeName;
    private Boolean isActive;
    private Team team;
    private ZonedDateTime startDate;
    private Set<String> trainingsTaken;
    private Set<String> testsTaken;
    private Status trainingStatus;

    @DynamoDBHashKey(attributeName = "employeeId")
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @DynamoDBAttribute(attributeName = "employeeName")
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    @DynamoDBAttribute(attributeName = "isActive")
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @DynamoDBTypeConvertedEnum
    @DynamoDBIndexHashKey(globalSecondaryIndexNames = EMPLOYEES_BY_TEAM_INDEX, attributeName = "team")
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @DynamoDBTypeConverted(converter = DateConverter.class)
    @DynamoDBAttribute(attributeName = "startDate")
    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the set of Tests associated with this Employee, null if there are none.
     *
     * @return Set of Tests for this Employee
     */
    @DynamoDBTypeConverted(converter = SetConverter.class)
    @DynamoDBAttribute(attributeName = "testsTaken")
    public Set<String> getTestsTaken() {
        // normally, we would prefer to return an empty Set if there is no
        // inventory, but DynamoDB doesn't represent empty Sets...needs to be null
        // instead
        if (null == testsTaken) {
            return null;
        }
        return new HashSet<>(testsTaken);
    }

    /**
     * Sets the Tests for this Employee as a copy of input, or null if input is null.
     *
     * @param testsTaken Set of Tests for this Employee
     */
    public void setTestsTaken(Set<String> testsTaken) {
        // see comment in getTrainingsTaken()
        if (null == testsTaken) {
            this.testsTaken = null;
        } else {
            this.testsTaken = new HashSet<>(testsTaken);
        }
    }

    /**
     * Returns the set of Trainings associated with this Employee, null if there are none.
     *
     * @return Set of Trainings for this Employee
     */
    @DynamoDBTypeConverted(converter = SetConverter.class)
    @DynamoDBAttribute(attributeName = "trainingsTaken")
    public Set<String> getTrainingsTaken() {
        // normally, we would prefer to return an empty Set if there is no
        // inventory, but DynamoDB doesn't represent empty Sets...needs to be null
        // instead
        if (null == trainingsTaken) {
            return null;
        }
        return new HashSet<>(trainingsTaken);
    }

    /**
     * Sets the Trainings for this Employee as a copy of input, or null if input is null.
     *
     * @param trainingsTaken Set of Tests for this Employee
     */
    public void setTrainingsTaken(Set<String> trainingsTaken) {
        // see comment in getTrainingsTaken()
        if (null == trainingsTaken) {
            this.trainingsTaken = null;
        } else {
            this.trainingsTaken = new HashSet<>(trainingsTaken);
        }
    }

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "trainingStatus")
    public Status getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(Status trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee that = (Employee) o;
        return employeeId.equals(that.employeeId) &&
                employeeName.equals(that.employeeName) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(team, that.team) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(trainingsTaken, that.trainingsTaken) &&
                Objects.equals(testsTaken, that.testsTaken) &&
                Objects.equals(trainingStatus, that.trainingStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, employeeName, isActive, team, startDate,
                trainingsTaken, testsTaken, trainingStatus);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName=" + employeeName +
                ", team='" + team + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
