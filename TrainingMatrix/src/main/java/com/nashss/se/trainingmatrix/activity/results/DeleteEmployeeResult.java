package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.EmployeeModel;

public class DeleteEmployeeResult {
    private final EmployeeModel employee;

    private DeleteEmployeeResult(EmployeeModel employee) {
        this.employee = employee;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    @Override
    public String toString() {
        return "DeleteEmployeeResult{" +
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

        public DeleteEmployeeResult build() {
            return new DeleteEmployeeResult(employee);
        }
    }
}
