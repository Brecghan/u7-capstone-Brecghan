package com.nashss.se.trainingmatrix.activity.requests;

public class GetTestListRequest {
    private final String trainingId;

    private final Boolean hasPassed;

    private final String employeeId;

    private GetTestListRequest(Boolean hasPassed, String trainingId, String employeeId) {
        this.hasPassed = hasPassed;
        this.trainingId = trainingId;
        this.employeeId = employeeId;
    }

    public Boolean getHasPassed() {
        return hasPassed;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String trainingId;

        private Boolean hasPassed;

        private String employeeId;


        public Builder withTrainingId(String trainingId) {
            if (trainingId.equals("null")) {
                this.trainingId = null;
            } else {
            this.trainingId = trainingId; }
            return this;
        }

        public Builder withHasPassed(String hasPassed) {
            if (hasPassed.equals("null")) {
                this.hasPassed = null;
            } else {
                this.hasPassed = Boolean.valueOf(hasPassed); }
            return this;
        }

        public Builder withEmployeeId(String employeeId) {
            if (employeeId.equals("null")) {
                this.employeeId = null;
            } else {
                this.employeeId = employeeId; }
            return this;
        }

        public GetTestListRequest build() {
            return new GetTestListRequest(hasPassed, trainingId, employeeId);
        }
    }
}
