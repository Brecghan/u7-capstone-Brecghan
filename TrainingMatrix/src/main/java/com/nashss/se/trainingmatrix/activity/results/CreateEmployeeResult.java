package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.EmployeeModel;

public class CreateEmployeeResult {
    private final EmployeeModel employee;

    private CreateEmployeeResult(EmployeeModel employee) {
        this.employee = employee;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    @Override
    public String toString() {
        return "CreateEmployeeResult{" +
                "employee=" + employee +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private EmployeeModel employee;

        public Builder withEmployee(EmployeeModel employee) {
            this.employee = employee;
            return this;
        }

        public CreateEmployeeResult build() {
            return new CreateEmployeeResult(employee);
        }
    }
}
