package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.EmployeeModel;

public class GetEmployeeResult {
    private final EmployeeModel employee;

    private GetEmployeeResult(EmployeeModel employee) {
        this.employee = employee;
    }

    public EmployeeModel getPantry() {
        return employee;
    }

    @Override
    public String toString() {
        return "GetEmployeeResult{" +
                "employee=" + employee +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private EmployeeModel employee ;

        public Builder withEmployee(EmployeeModel employee) {
            this.employee = employee;
            return this;
        }

        public GetEmployeeResult build() {
            return new GetEmployeeResult(employee);
        }
    }
}
