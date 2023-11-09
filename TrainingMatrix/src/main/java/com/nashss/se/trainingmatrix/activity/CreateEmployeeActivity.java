package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.CreateEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateEmployeeResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.models.EmployeeModel;

import java.time.ZonedDateTime;
import java.util.HashSet;
import javax.inject.Inject;

/**
 * Implementation of the CreateEmployeeActivity for the TrainingMatrix's CreateEmployee API.
 * <p>
 * This API allows the customer to create a new employee.
 */
public class CreateEmployeeActivity {
    private final EmployeeDao employeeDao;

    /**
     * Instantiates a new CreateEmployeeActivity object.
     *
     * @param employeeDao EmployeeDao to access the employees table.
     */
    @Inject
    public CreateEmployeeActivity(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    /**
     * This method handles the incoming request by persisting a new employee
     * with the provided employee name, employee ID, start date, and team.
     * <p>
     * It then returns the newly created employee.
     * <p>
     *
     * @param createEmployeeRequest request object containing the employee name, employee ID, start date, and team
     *                              associated with it
     * @return createEmployeeResult result object containing the API defined {@link EmployeeModel}
     */
    public CreateEmployeeResult handleRequest(final CreateEmployeeRequest createEmployeeRequest) {
        Employee newEmployee = new Employee();
        newEmployee.setEmployeeName(createEmployeeRequest.getEmployeeName());
        newEmployee.setEmployeeId(createEmployeeRequest.getEmployeeId());
        newEmployee.setIsActive(true);
        newEmployee.setTeam(createEmployeeRequest.getTeam());
        newEmployee.setStartDate(ZonedDateTime.parse(createEmployeeRequest.getStartDate()));
        newEmployee.setTestsTaken(new HashSet<>());
        newEmployee.setTrainingsTaken(new HashSet<>());
        newEmployee.setTrainingStatus(Status.UP_TO_DATE);

        employeeDao.saveEmployee(newEmployee);

        EmployeeModel employeeModel = new ModelConverter().toEmployeeModel(newEmployee);
        return CreateEmployeeResult.builder()
                .withEmployee(employeeModel)
                .build();
    }
}
