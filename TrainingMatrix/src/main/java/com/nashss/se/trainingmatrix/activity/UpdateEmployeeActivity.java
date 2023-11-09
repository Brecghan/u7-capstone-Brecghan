package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;

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
}
