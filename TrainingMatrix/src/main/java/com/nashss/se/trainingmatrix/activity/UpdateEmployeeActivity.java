package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.UpdateEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.UpdateEmployeeResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.models.EmployeeModel;

import java.util.Set;
import javax.inject.Inject;

public class UpdateEmployeeActivity {
    private final EmployeeDao employeeDao;

    /**
     * Instantiates a new UpdateEmployeeActivity object.
     *
     * @param employeeDao   EmployeeDao to access the Employees table.
     */
    @Inject
    public UpdateEmployeeActivity(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    /**
     * This method handles the incoming request by retrieving an employee using the employee ID and updating
     * with the provided employee name, team, isActive, trainingStatus, and trainingsTaken.
     * <p>
     * It then returns the newly updated employee.
     * <p>
     *
     * @param updateEmployeeRequest request object containing the employee name, employee ID, start date, team, 
     *                              isActive, trainingStatus, and trainingsTaken associated with it
     * @return updateEmployeeResult result object containing the API defined {@link EmployeeModel}
     */
    public UpdateEmployeeResult handleRequest(final UpdateEmployeeRequest updateEmployeeRequest) {
        Employee employee = employeeDao.getEmployee(updateEmployeeRequest.getEmployeeId());

        if (updateEmployeeRequest.getEmployeeName() != null) {
            employee.setEmployeeName(updateEmployeeRequest.getEmployeeName());
        }
        if (updateEmployeeRequest.getIsActive() != null) {
            employee.setIsActive(updateEmployeeRequest.getIsActive());
        }
        if (updateEmployeeRequest.getTeam() != null) {
            employee.setTeam(updateEmployeeRequest.getTeam());
        }

        if (updateEmployeeRequest.getTestsTaken() != null) {
        Set<String> updateTests = employee.getTestsTaken();
        updateTests.addAll(updateEmployeeRequest.getTestsTaken());
        employee.setTestsTaken(updateTests); }

        if (updateEmployeeRequest.getTrainingsTaken() != null) {
        Set<String> updateTrainings = employee.getTrainingsTaken();
        updateTrainings.addAll(updateEmployeeRequest.getTrainingsTaken());
        employee.setTrainingsTaken(updateTrainings); }

        employeeDao.saveEmployee(employee);

        EmployeeModel employeeModel = new ModelConverter().toEmployeeModel(employee);
        return UpdateEmployeeResult.builder()
                .withEmployee(employeeModel)
                .build();
    }
}
