package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.CreateEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateEmployeeResult;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateEmployeeActivityTest {
    @Mock
    private EmployeeDao employeeDao;

    private CreateEmployeeActivity createEmployeeActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createEmployeeActivity = new CreateEmployeeActivity(employeeDao);
    }

    @Test
    public void handleRequest_withGoodData_createsAndSavesEmployee() {
        // GIVEN
        String expectedName = "expectedName";
        String expectedEmployeeId = "expectedEmployeeId";

        CreateEmployeeRequest request = CreateEmployeeRequest.builder()
                .withEmployeeName(expectedName)
                .withEmployeeId(expectedEmployeeId)
                .withTeam(Team.INNOVATION)
                .withStartDate(String.valueOf(ZonedDateTime.now()))
                .build();

        // WHEN
        CreateEmployeeResult result = createEmployeeActivity.handleRequest(request);

        // THEN
        verify(employeeDao).saveEmployee(any(Employee.class));

        assertEquals(expectedName, result.getEmployee().getEmployeeName());
        assertEquals(expectedEmployeeId, result.getEmployee().getEmployeeId());
    }

}
