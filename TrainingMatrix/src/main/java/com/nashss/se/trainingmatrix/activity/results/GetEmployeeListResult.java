package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.EmployeeModel;

import java.util.List;

public class GetEmployeeListResult {
    private final List<EmployeeModel> employees;

    private GetEmployeeListResult(List<EmployeeModel> employees) {
        this.employees = employees;
    }

    public List<EmployeeModel> getEmployees() {
        return employees;
    }

    @Override
    public String toString() {
        return "GetEmployeeListResult{" +
                "employees=" + employees +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<EmployeeModel> employees ;

        public Builder withEmployees(List<EmployeeModel> employees) {
            this.employees = employees;
            return this;
        }

        public GetEmployeeListResult build() {
            return new GetEmployeeListResult(employees);
        }
    }
}
