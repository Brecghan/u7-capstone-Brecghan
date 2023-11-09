package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;

import javax.inject.Inject;

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
}
