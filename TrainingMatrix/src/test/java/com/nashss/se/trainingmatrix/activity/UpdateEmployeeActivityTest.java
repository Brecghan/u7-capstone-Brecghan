package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.UpdateEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.UpdateEmployeeResult;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UpdateEmployeeActivityTest {
    @Mock
    private EmployeeDao employeeDao;


    private UpdateEmployeeActivity updateEmployeeActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        updateEmployeeActivity = new UpdateEmployeeActivity(employeeDao);
    }

    @Test
    public void handleRequest_goodRequest_updatesEmployeeTable() {
        // GIVEN
        String employeeName = "name";
        String employeeId = "id";
        String testID = "testId";
        String trainingId = "trainingId";
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeName("Original Name");
        employee.setTestsTaken(new HashSet<>());
        employee.setTrainingsTaken(new HashSet<>());
        employee.setTeam(Team.INNOVATION);
        employee.setStartDate(ZonedDateTime.now());

        UpdateEmployeeRequest request = UpdateEmployeeRequest.builder()
                .withEmployeeId(employeeId)
                .withEmployeeName(employeeName)
                .withTestsTaken(testID)
                .withTrainingsTaken(trainingId)
                .build();


        when(employeeDao.getEmployee(employeeId)).thenReturn(employee);
        when(employeeDao.saveEmployee(any(Employee.class))).thenReturn(employee);

        // WHEN
        UpdateEmployeeResult result = updateEmployeeActivity.handleRequest(request);

        // THEN
        assertEquals(employeeName, result.getEmployee().getEmployeeName());
        assertTrue(result.getEmployee().getTestsTaken().contains(testID));
        assertTrue(result.getEmployee().getTrainingsTaken().contains(trainingId));
    }

}
