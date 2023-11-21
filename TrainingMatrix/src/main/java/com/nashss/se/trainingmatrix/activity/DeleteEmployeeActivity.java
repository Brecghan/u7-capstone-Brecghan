package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.DeleteEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.DeleteEmployeeResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.models.EmployeeModel;

import javax.inject.Inject;

public class DeleteEmployeeActivity {
    private final EmployeeDao employeeDao;

    /**
     * Instantiates a new DeleteEmployeeActivity object.
     *
     * @param employeeDao   EmployeeDao to access the Employees table.
     */
    @Inject
    public DeleteEmployeeActivity(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    /**
     * This method handles the incoming request by retrieving an employee using the employee ID and updating
     * the isActive variable for that employee to false.
     * <p>
     * It then returns the deleted employee.
     * <p>
     *
     * @param deleteEmployeeRequest request object containing the employee ID
     * @return deleteEmployeeResult result object containing the API defined {@link EmployeeModel}
     */
    public DeleteEmployeeResult handleRequest(final DeleteEmployeeRequest deleteEmployeeRequest) {
        Employee employee = employeeDao.getEmployee(deleteEmployeeRequest.getEmployeeId());

        employee.setIsActive(false);

        employeeDao.saveEmployee(employee);

        EmployeeModel employeeModel = new ModelConverter().toEmployeeModel(employee);
        return DeleteEmployeeResult.builder()
                .withEmployee(employeeModel)
                .build();
    }
}
