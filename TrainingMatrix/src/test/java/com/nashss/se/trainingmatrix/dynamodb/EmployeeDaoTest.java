package com.nashss.se.trainingmatrix.dynamodb;

import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;
import com.nashss.se.trainingmatrix.exceptions.EmployeeNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class EmployeeDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private EmployeeDao employeeDao;

    @Mock
    PaginatedScanList<Employee> paginatedScanList;

    @Mock
    PaginatedQueryList<Employee> paginatedQueryList;

    @Captor
    ArgumentCaptor<DynamoDBScanExpression> scanCaptor;

    @Captor
    ArgumentCaptor<DynamoDBQueryExpression<Employee>> queryCaptor;

    @BeforeEach
    public void setup() {
        initMocks(this);
        employeeDao = new EmployeeDao(dynamoDBMapper);
    }

    @Test
    public void getEmployee_withEmployeeId_callsMapperWithPartitionKey() {
        // GIVEN
        String employeeId = "employeeId";
        when(dynamoDBMapper.load(Employee.class, employeeId)).thenReturn(new Employee());

        // WHEN
        Employee employee = employeeDao.getEmployee(employeeId);

        // THEN
        assertNotNull(employee);
        verify(dynamoDBMapper).load(Employee.class, employeeId);
    }

    @Test
    public void getEmployee_employeeIdNotFound_throwsEmployeeNotFoundException() {
        // GIVEN
        String nonexistentEmployeeId = "NotReal";
        when(dynamoDBMapper.load(Employee.class, nonexistentEmployeeId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(EmployeeNotFoundException.class, () -> employeeDao.getEmployee(nonexistentEmployeeId));
    }

    @Test
    public void saveEmployee_callsMapperWithEmployee() {
        // GIVEN
        Employee employee = new Employee();

        // WHEN
        Employee result = employeeDao.saveEmployee(employee);

        // THEN
        verify(dynamoDBMapper).save(employee);
        assertEquals(employee, result);
    }

    @Test
    public void getEmployeeList_withNoParameters_callsMapperScan() {
        // GIVEN
        when(dynamoDBMapper.scan(eq(Employee.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanList);

        // WHEN
        List<Employee> employees = employeeDao.getEmployeeList(false, null);

        // THEN
        assertNotNull(employees);
        verify(dynamoDBMapper).scan(eq(Employee.class), any(DynamoDBScanExpression.class));
    }

    @Test
    public void getEmployeeList_withActiveParameter_callsMapperScan() {
        // GIVEN
        when(dynamoDBMapper.scan(eq(Employee.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanList);

        // WHEN
        List<Employee> employees = employeeDao.getEmployeeList(true, null);

        // THEN
        verify(dynamoDBMapper).scan(eq(Employee.class), scanCaptor.capture());
        assertNotNull(employees);
        verify(dynamoDBMapper).scan(eq(Employee.class), any(DynamoDBScanExpression.class));
        assertFalse(scanCaptor.getValue().getFilterExpression().isEmpty());
    }

    @Test
    public void getEmployeeList_withTeam_callsGSIMapperQuery() {
        // GIVEN
        when(dynamoDBMapper.query(eq(Employee.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        // WHEN
        List<Employee> employees = employeeDao.getEmployeeList(true, Team.INNOVATION);

        // THEN
        verify(dynamoDBMapper).query(eq(Employee.class), queryCaptor.capture());
        assertNotNull(employees);
        verify(dynamoDBMapper).query(eq(Employee.class), any(DynamoDBQueryExpression.class));
        assertFalse(queryCaptor.getValue().getKeyConditionExpression().isEmpty());
        assertEquals(Employee.EMPLOYEES_BY_TEAM_INDEX, queryCaptor.getValue().getIndexName());
    }
}