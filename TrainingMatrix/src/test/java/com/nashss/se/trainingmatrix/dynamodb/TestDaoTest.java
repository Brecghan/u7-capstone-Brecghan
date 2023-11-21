package com.nashss.se.trainingmatrix.dynamodb;

import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.Test;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;
import com.nashss.se.trainingmatrix.exceptions.TestNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class TestDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private TestDao testDao;

    @Mock
    PaginatedScanList<Test> paginatedScanList;

    @Mock
    PaginatedQueryList<Test> paginatedQueryList;


    @Captor
    ArgumentCaptor<DynamoDBQueryExpression<Test>> queryCaptor;

    @BeforeEach
    public void setup() {
        initMocks(this);
        testDao = new TestDao(dynamoDBMapper);
    }

    @org.junit.jupiter.api.Test
    public void getTest_withTestId_callsMapperWithPartitionKey() {
        // GIVEN
        String trainingId = "trainingId";
        String employeeId = "employeeId";
        when(dynamoDBMapper.load(Test.class, trainingId, employeeId)).thenReturn(new Test());

        // WHEN
        Test test = testDao.getTest(trainingId, employeeId);

        // THEN
        assertNotNull(test);
        verify(dynamoDBMapper).load(Test.class, trainingId, employeeId);
    }

    @org.junit.jupiter.api.Test
    public void getTest_testIdNotFound_throwsTestNotFoundException() {
        // GIVEN
        String nonexistentTrainingId = "NotReal";
        String nonexistentEmployeeId = "NotReal";
        when(dynamoDBMapper.load(Test.class, nonexistentTrainingId, nonexistentEmployeeId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(TestNotFoundException.class, () -> testDao.getTest(nonexistentTrainingId, nonexistentEmployeeId));
    }

    @org.junit.jupiter.api.Test
    public void saveTest_callsMapperWithTest() {
        // GIVEN
        Test test = new Test();

        // WHEN
        Test result = testDao.saveTest(test);

        // THEN
        verify(dynamoDBMapper).save(test);
        assertEquals(test, result);
    }

    @org.junit.jupiter.api.Test
    public void getTestListPassSpecific_withNoParameters_callsMapperScanWholeTable() {
        // GIVEN
        when(dynamoDBMapper.scan(eq(Test.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanList);

        // WHEN
        List<Test> tests = testDao.getTestListPassSpecific(false,null, null);

        // THEN
        assertNotNull(tests);
        verify(dynamoDBMapper).scan(eq(Test.class), any(DynamoDBScanExpression.class));
    }

    @org.junit.jupiter.api.Test
    public void getTestListPassSpecific_withTrainingParameter_callsMapperQuery() {
        // GIVEN
        String trainingId = "trainingId";
        Test test = new Test();
        test.setTrainingId(trainingId);
        when(dynamoDBMapper.query(eq(Test.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        // WHEN
        List<Test> tests = testDao.getTestListPassSpecific(true, trainingId,null);

        // THEN
        verify(dynamoDBMapper).query(eq(Test.class), queryCaptor.capture());
        assertNotNull(tests);
        verify(dynamoDBMapper).query(eq(Test.class), any(DynamoDBQueryExpression.class));
        assertFalse(queryCaptor.getValue().getFilterExpression().isEmpty());
        assertEquals(test, queryCaptor.getValue().getHashKeyValues());
    }

    @org.junit.jupiter.api.Test
    public void getTestListPassSpecific_withEmployee_callsGSIMapperQuery() {
        // GIVEN
        String employeeId = "employeeId";
        when(dynamoDBMapper.query(eq(Test.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        // WHEN
        List<Test> tests = testDao.getTestListPassSpecific(true, null, employeeId);

        // THEN
        verify(dynamoDBMapper).query(eq(Test.class), queryCaptor.capture());
        assertNotNull(tests);
        verify(dynamoDBMapper).query(eq(Test.class), any(DynamoDBQueryExpression.class));
        assertFalse(queryCaptor.getValue().getKeyConditionExpression().isEmpty());
        assertEquals(Test.TESTS_BY_EMPLOYEE_INDEX, queryCaptor.getValue().getIndexName());
    }

    @org.junit.jupiter.api.Test
    public void getTestList_withNoParameters_callsMapperScanWholeTable() {
        // GIVEN
        when(dynamoDBMapper.scan(eq(Test.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanList);

        // WHEN
        List<Test> tests = testDao.getTestList(null, null);

        // THEN
        assertNotNull(tests);
        verify(dynamoDBMapper).scan(eq(Test.class), any(DynamoDBScanExpression.class));
    }

    @org.junit.jupiter.api.Test
    public void getTestList_withTrainingParameter_callsMapperQuery() {
        // GIVEN
        String trainingId = "trainingId";
        Test test = new Test();
        test.setTrainingId(trainingId);
        when(dynamoDBMapper.query(eq(Test.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        // WHEN
        List<Test> tests = testDao.getTestList(trainingId,null);

        // THEN
        verify(dynamoDBMapper).query(eq(Test.class), queryCaptor.capture());
        assertNotNull(tests);
        verify(dynamoDBMapper).query(eq(Test.class), any(DynamoDBQueryExpression.class));
        assertEquals(test, queryCaptor.getValue().getHashKeyValues());
    }

    @org.junit.jupiter.api.Test
    public void getTestList_withEmployee_callsGSIMapperQuery() {
        // GIVEN
        String employeeId = "employeeId";
        when(dynamoDBMapper.query(eq(Test.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        // WHEN
        List<Test> tests = testDao.getTestList(null, employeeId);

        // THEN
        verify(dynamoDBMapper).query(eq(Test.class), queryCaptor.capture());
        assertNotNull(tests);
        verify(dynamoDBMapper).query(eq(Test.class), any(DynamoDBQueryExpression.class));
        assertFalse(queryCaptor.getValue().getKeyConditionExpression().isEmpty());
        assertEquals(Test.TESTS_BY_EMPLOYEE_INDEX, queryCaptor.getValue().getIndexName());
    }
}