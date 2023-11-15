package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetEmployeeListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetEmployeeListResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.models.EmployeeModel;

import javax.inject.Inject;
import java.util.List;

public class GetEmployeeListActivity {
    private final EmployeeDao employeeDao;

    /**
     * Instantiates a new GetEmployeeListActivity object.
     *
     * @param employeeDao   EmployeeDao to access the Employees table.
     */
    @Inject
    public GetEmployeeListActivity(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    /**
     * This method handles the incoming request by retrieving List of Employees from the database.
     * <p>
     * It then returns the matching employees, or an empty result list if none are found.
     *
     * @param getEmployeeListRequest request object containing the Parameters of isActive & Team
     * @return getEmployeeListResult result object containing the employees that matched the passed parameters
     */
    public GetEmployeeListResult handleRequest(final GetEmployeeListRequest getEmployeeListRequest) {

        List<Employee> results = employeeDao.getEmployeeList(getEmployeeListRequest.getIsActive(),getEmployeeListRequest.getTeam());
        List<EmployeeModel> employeeModels = new ModelConverter().toEmployeeModelList(results);

        return GetEmployeeListResult.builder()
                .withEmployees(employeeModels)
                .build();
    }
}
