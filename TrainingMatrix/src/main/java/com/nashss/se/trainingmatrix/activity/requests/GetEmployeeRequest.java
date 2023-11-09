package com.nashss.se.trainingmatrix.activity.requests;

public class GetEmployeeRequest {
    private final String employeeId;

    private GetEmployeeRequest(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    @Override
    public String toString() {
        return "GetEmployeeRequest{" +
                "employeeId='" + employeeId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String employeeId;

        public Builder withEmployeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public GetEmployeeRequest build() {
            return new GetEmployeeRequest(employeeId);
        }
    }
}
