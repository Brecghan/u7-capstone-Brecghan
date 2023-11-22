package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.DeleteEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.DeleteEmployeeResult;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class DeleteEmployeeActivityTest {

    @Mock
    private EmployeeDao employeeDao;

    private DeleteEmployeeActivity deleteEmployeeActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        deleteEmployeeActivity = new DeleteEmployeeActivity(employeeDao);
    }

    @Test
    public void handleRequest_goodRequest_setsTrainingToInactive() {
        // GIVEN
        String employeeName = "name";
        String employeeId = "trainingId";

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeName(employeeName);
        employee.setIsActive(true);
        employee.setTrainingsTaken(new HashSet<>());
        employee.setTestsTaken(new HashSet<>());
        employee.setTrainingStatus(Status.UP_TO_DATE);
        employee.setStartDate(ZonedDateTime.now());

        DeleteEmployeeRequest request = DeleteEmployeeRequest.builder()
                .withEmployeeId(employeeId)
                .build();

        when(employeeDao.getEmployee(any(String.class))).thenReturn(employee);
        when(employeeDao.saveEmployee(any(Employee.class))).thenReturn(employee);

        // WHEN
        DeleteEmployeeResult result = deleteEmployeeActivity.handleRequest(request);

        // THEN
        assertEquals(employeeId, result.getEmployee().getEmployeeId());
        assertFalse(result.getEmployee().getIsActive());
    }

}
