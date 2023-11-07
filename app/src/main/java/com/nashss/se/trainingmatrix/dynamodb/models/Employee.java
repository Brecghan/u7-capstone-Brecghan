package com.nashss.se.trainingmatrix.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import com.nashss.se.trainingmatrix.converters.DateConverter;
import com.nashss.se.trainingmatrix.converters.SetConverter;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@DynamoDBTable(tableName = "Employees")
public class Employee {
    private String employeeId;
    private String employeeName;
    private Boolean isActive;
    private Enum<Team> team;
    private Date startDate;
    private Set<String> trainingsTaken;
    private Set<String> testsTaken;
    private Enum<Status> trainingStatus;

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
    @DynamoDBAttribute(attributeName = "team")
    public Enum<Team> getTeam() {
        return team;
    }

    public void setTeam(Enum<Team> team) {
        this.team = team;
    }

    @DynamoDBTypeConverted(converter = DateConverter.class)
    @DynamoDBAttribute(attributeName = "startDate")
    public Date getStartDate() {
        return new Date(startDate.getTime());
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

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

    public void setTestsTaken(Set<String> testsTaken) {
        // see comment in getTrainingsTaken()
        if (null == testsTaken) {
            this.testsTaken = null;
        } else {
            this.testsTaken = new HashSet<>(testsTaken);
        }
    }

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
    public Enum<Status> getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(Enum<Status> trainingStatus) {
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
                Objects.equals(albumName, that.albumName) &&
                Objects.equals(songTitle, that.songTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asin, trackNumber, albumName, songTitle);
    }

    @Override
    public String toString() {
        return "AlbumTrack{" +
                "asin='" + asin + '\'' +
                ", trackNumber=" + trackNumber +
                ", albumName='" + albumName + '\'' +
                ", songTitle='" + songTitle + '\'' +
                '}';
    }
}
