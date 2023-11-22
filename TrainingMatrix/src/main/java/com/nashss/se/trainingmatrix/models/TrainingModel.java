package com.nashss.se.trainingmatrix.models;

import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TrainingModel {
    private final String trainingId;
    private final String trainingName;
    private final Boolean isActive;
    private final Integer monthsTilExpire;
    private final String trainingDate;
    private final Set<String> employeesTrained;
    private final Set<String> testsForTraining;
    private final Status expirationStatus;
    private final String trainingSeries;
    private TrainingModel(String trainingId, String trainingName, Boolean isActive, Integer monthsTilExpire,
                          String trainingDate, Set<String> employeesTrained,  Set<String> testsForTraining,
                          Status expirationStatus,  String trainingSeries) {

        this.trainingId = trainingId;
        this.trainingName = trainingName;
        this.isActive = isActive;
        this.monthsTilExpire = monthsTilExpire;
        this.trainingDate = trainingDate;
        this.employeesTrained = employeesTrained;
        this.testsForTraining = testsForTraining;
        this.expirationStatus = expirationStatus;
        this.trainingSeries = trainingSeries;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Integer getMonthsTilExpire() {
        return monthsTilExpire;
    }

    public String getTrainingDate() {
        return trainingDate;
    }

    public Set<String> getEmployeesTrained() {
        return employeesTrained;
    }

    public Set<String> getTestsForTraining() {
        return testsForTraining;
    }

    public Status getExpirationStatus() {
        return expirationStatus;
    }

    public String getTrainingSeries() {
        return trainingSeries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrainingModel that = (TrainingModel) o;
        return Objects.equals(trainingId, that.trainingId) &&
                Objects.equals(trainingName, that.trainingName) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(monthsTilExpire, that.monthsTilExpire) &&
                Objects.equals(trainingDate, that.trainingDate) &&
                Objects.equals(employeesTrained, that.employeesTrained) &&
                Objects.equals(testsForTraining, that.testsForTraining) &&
                expirationStatus == that.expirationStatus &&
                Objects.equals(trainingSeries, that.trainingSeries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingId, trainingName, isActive, monthsTilExpire,
                trainingDate, employeesTrained, testsForTraining, expirationStatus, trainingSeries);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String trainingId;
        private String trainingName;
        private Boolean isActive;
        private Integer monthsTilExpire;
        private String trainingDate;
        private Set<String> employeesTrained;
        private Set<String> testsForTraining;
        private Status expirationStatus;
        private String trainingSeries;

        public Builder withTrainingId(String trainingId) {
            this.trainingId = trainingId;
            return this;
        }

        public Builder withTrainingName(String trainingName) {
            this.trainingName = trainingName;
            return this;
        }

        public Builder withIsActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder withMonthsTilExpire(Integer monthsTilExpire) {
            this.monthsTilExpire = monthsTilExpire;
            return this;
        }

        public Builder withTrainingDate(ZonedDateTime trainingDate) {
            this.trainingDate = trainingDate.toString();
            return this;
        }

        public Builder withEmployeesTrained(Set<String> employeesTrained) {
            if (null == employeesTrained) {
                this.employeesTrained = null;
            } else {
                this.employeesTrained = new HashSet<>(employeesTrained);
            }
            return this;
        }

        public Builder withTestsForTraining(Set<String> testsForTraining) {
            if (null == testsForTraining) {
                this.testsForTraining = null;
            } else {
                this.testsForTraining = new HashSet<>(testsForTraining);
            }
            return this;
        }

        public Builder withExpirationStatus(Status expirationStatus) {
            this.expirationStatus = expirationStatus;
            return this;
        }

        public Builder withTrainingSeries(String trainingSeries) {
            this.trainingSeries = trainingSeries;
            return this;
        }

        public TrainingModel build() {
            return new TrainingModel(trainingId, trainingName, isActive, monthsTilExpire,
                    trainingDate, employeesTrained, testsForTraining,
                    expirationStatus, trainingSeries);
        }
    }
}
