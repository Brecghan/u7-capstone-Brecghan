package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetEmployeeListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetEmployeeListResult;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetEmployeeListActivityTest {
    @Mock
    private EmployeeDao employeeDao;

    private GetEmployeeListActivity getEmployeeListActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getEmployeeListActivity = new GetEmployeeListActivity(employeeDao);
    }

    @Test
    public void handleRequest_savedEmployeeListFound_returnsEmployeeModelListInResult() {
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

        Employee employee2 = new Employee();
        employee2.setEmployeeId(expectedId);
        employee2.setEmployeeName(expectedName);
        employee2.setIsActive(false);
        employee2.setTeam(expectedTeam);
        employee2.setStartDate(expectedDate);
        employee2.setTestsTaken(new HashSet<>());
        employee2.setTrainingsTaken(new HashSet<>());
        employee2.setTrainingStatus(expectedStatus);

        List<Employee> employeeList = List.of(employee, employee2);

        when(employeeDao.getEmployeeList(eq(true), any(Team.class))).thenReturn(employeeList);

        GetEmployeeListRequest request = GetEmployeeListRequest.builder()
                .withTeam(String.valueOf(expectedTeam))
                .withIsActive(true)
                .build();

        // WHEN
        GetEmployeeListResult result = getEmployeeListActivity.handleRequest(request);

        // THEN
        assertEquals(expectedId, result.getEmployees().get(0).getEmployeeId());
        assertEquals(expectedName, result.getEmployees().get(0).getEmployeeName());
        assertEquals(expectedTeam, result.getEmployees().get(0).getTeam());
        assertEquals(expectedStatus, result.getEmployees().get(0).getTrainingStatus());
        assertEquals(String.valueOf(expectedDate), result.getEmployees().get(0).getStartDate());
        assertEquals(true, result.getEmployees().get(0).getActive());
        assertEquals(false, result.getEmployees().get(1).getActive());
    }
}