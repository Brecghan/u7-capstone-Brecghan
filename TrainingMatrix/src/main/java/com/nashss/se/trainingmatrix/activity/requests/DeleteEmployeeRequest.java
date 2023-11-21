package com.nashss.se.trainingmatrix.activity.requests;

public class DeleteEmployeeRequest {
    private final String employeeId;

    private DeleteEmployeeRequest(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    @Override
    public String toString() {
        return "DeleteEmployeeRequest{" +
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

        public DeleteEmployeeRequest build() {
            return new DeleteEmployeeRequest(employeeId);
        }
    }
}
