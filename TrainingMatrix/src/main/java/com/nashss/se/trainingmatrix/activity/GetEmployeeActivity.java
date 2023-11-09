package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.GetEmployeeResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.models.EmployeeModel;

import javax.inject.Inject;

public class GetEmployeeActivity {
    private final EmployeeDao employeeDao;

    /**
     * Instantiates a new GetEmployeeActivity object.
     *
     * @param employeeDao EmployeeDao to access the pantry table.
     */
    @Inject
    public GetEmployeeActivity(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    /**
     * This method handles the incoming request by retrieving an employee from the database.
     * <p>
     * It then returns the matching employee.
     *
     * @param getEmployeeRequest request object containing the User ID & the Employee Id
     * @return getEmployeeResult result object containing the Employee requested that were created by that User ID
     */
    public GetEmployeeResult handleRequest(final GetEmployeeRequest getEmployeeRequest) {

        Employee result = employeeDao.getEmployee(getEmployeeRequest.getEmployeeId());
        EmployeeModel employeeModel = new ModelConverter().toEmployeeModel(result);

        return GetEmployeeResult.builder()
                .withEmployee(employeeModel)
                .build();
    }
}
