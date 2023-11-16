package com.nashss.se.trainingmatrix.models;

import java.util.List;
import java.util.Objects;

public class TestModel {

    private final String trainingId;
    private final String employeeId;
    private final Boolean hasPassed;
    private final Integer scoreToPass;
    private final Integer latestScore;
    private final List<String> testAttempts;

    private TestModel(String trainingId, String employeeId, Boolean hasPassed, Integer scoreToPass, Integer latestScore,
                      List<String> testAttempts) {
        this.employeeId = employeeId;
        this.trainingId = trainingId;
        this.hasPassed = hasPassed;
        this.scoreToPass = scoreToPass;
        this.latestScore = latestScore;
        this.testAttempts = testAttempts;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Boolean getHasPassed() {
        return hasPassed;
    }

    public Integer getScoreToPass() {
        return scoreToPass;
    }

    public Integer getLatestScore() {
        return latestScore;
    }

    public List<String> getTestAttempts() {
        return testAttempts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestModel testModel = (TestModel) o;
        return Objects.equals(trainingId, testModel.trainingId) &&
                Objects.equals(employeeId, testModel.employeeId) &&
                Objects.equals(hasPassed, testModel.hasPassed) &&
                Objects.equals(scoreToPass, testModel.scoreToPass) &&
                Objects.equals(latestScore, testModel.latestScore) &&
                Objects.equals(testAttempts, testModel.testAttempts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingId, employeeId, hasPassed, scoreToPass, latestScore, testAttempts);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String trainingId;
        private String employeeId;
        private Boolean hasPassed;
        private Integer scoreToPass;
        private Integer latestScore;
        private List<String> testAttempts;

        public Builder withTrainingId(String trainingId) {
            this.trainingId = trainingId;
            return this;
        }

        public Builder withEmployeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder withHasPassed(Boolean hasPassed) {
            this.hasPassed = hasPassed;
            return this;
        }

        public Builder withScoreToPass(Integer scoreToPass) {
            this.scoreToPass = scoreToPass;
            return this;
        }

        public Builder withLatestScore(Integer latestScore) {
            this.latestScore = latestScore;
            return this;
        }

        public Builder withTestAttempts(List<String> testAttempts) {
            this.testAttempts = testAttempts;
            return this;
        }

        public TestModel build() {
            return new TestModel(trainingId, employeeId, hasPassed, scoreToPass, latestScore, testAttempts);
        }
    }
}
