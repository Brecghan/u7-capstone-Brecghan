package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.GetEmployeeResult;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetEmployeeActivityTest {
    @Mock
    private EmployeeDao employeeDao;

    private GetEmployeeActivity getEmployeeActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getEmployeeActivity = new GetEmployeeActivity(employeeDao);
    }

    @Test
    public void handleRequest_savedEmployeeFound_returnsEmployeeModelInResult() {
        // GIVEN
        String expectedId = "expectedId";
        String expectedName = "expectedName";
        Team expectedTeam = Team.INNOVATION;
        ZonedDateTime expectedDate = ZonedDateTime.now();
        Status expectedStatus = Status.UP_TO_DATE;

        Employee employee = new Employee();
        employee.setEmployeeId(expectedId);
        employee.setEmployeeName(expectedName);
        employee.setIsActive(true);
        employee.setTeam(expectedTeam);
        employee.setStartDate(expectedDate);
        employee.setTestsTaken(new HashSet<>());
        employee.setTrainingsTaken(new HashSet<>());
        employee.setTrainingStatus(expectedStatus);

        when(employeeDao.getEmployee(expectedId)).thenReturn(employee);

        GetEmployeeRequest request = GetEmployeeRequest.builder()
            .withEmployeeId(expectedId)
            .build();

        // WHEN
        GetEmployeeResult result = getEmployeeActivity.handleRequest(request);

        // THEN
        assertEquals(expectedId, result.getEmployee().getEmployeeId());
        assertEquals(expectedName, result.getEmployee().getEmployeeName());
        assertEquals(expectedTeam, result.getEmployee().getTeam());
        assertEquals(expectedStatus, result.getEmployee().getTrainingStatus());
        assertEquals(String.valueOf(expectedDate), result.getEmployee().getStartDate());
    }
}