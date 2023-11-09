package com.nashss.se.trainingmatrix.converters;

import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.models.EmployeeModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {
    /**
     * Converts a provided {@link Employee} into an {@link EmployeeModel} representation.
     *
     * @param employee the employee to convert
     * @return the converted employee
     */
    public EmployeeModel toEmployeeModel(Employee employee) {
        Set<String> testsTaken = null;
        Set<String> trainingsTaken = null;
        if (employee.getTestsTaken() != null) {
            testsTaken = new HashSet<>(employee.getTestsTaken());
        }
        if (employee.getTrainingsTaken() != null) {
            trainingsTaken = new HashSet<>(employee.getTrainingsTaken());
        }

        return EmployeeModel.builder()
                .withEmployeeId(employee.getEmployeeId())
                .withEmployeeName(employee.getEmployeeName())
                .withActive(employee.getIsActive())
                .withTeam(employee.getTeam())
                .withStartDate(employee.getStartDate())
                .withTrainingsTaken(trainingsTaken)
                .withTestsTaken(testsTaken)
                .withTrainingStatus(employee.getTrainingStatus())
                .build();
    }

    /**
     * Converts a list of Employees to a list of EmployeeModels.
     *
     * @param employees The Employees to convert to EmployeeModels
     * @return The converted list of EmployeeModels
     */
    public List<EmployeeModel> toEmployeeModelList(List<Employee> employees) {
        List<EmployeeModel> employeeModels = new ArrayList<>();

        for (Employee employee : employees) {
            employeeModels.add(toEmployeeModel(employee));
        }

        return employeeModels;
    }
}
