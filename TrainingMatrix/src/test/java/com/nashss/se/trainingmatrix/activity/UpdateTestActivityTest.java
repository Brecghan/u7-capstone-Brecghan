package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.UpdateTestRequest;
import com.nashss.se.trainingmatrix.activity.results.UpdateTestResult;
import com.nashss.se.trainingmatrix.dynamodb.TestDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Test;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;
import com.nashss.se.trainingmatrix.utils.NameConverter;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import javax.print.DocFlavor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UpdateTestActivityTest {
    @Mock
    private TestDao testDao;


    private UpdateTestActivity updateTestActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        updateTestActivity = new UpdateTestActivity(testDao);
    }

    @org.junit.jupiter.api.Test
    public void handleRequest_goodRequest_updatesTestTable() {
        // GIVEN
        String employeeId = "employeeId";
        String trainingId = "trainingId";
        Test test = new Test();
        test.setEmployeeId(employeeId);
        test.setTrainingId(trainingId);
        test.setLatestScore(null);
        test.setHasPassed(false);
        test.setTestAttempts(new ArrayList<>());
        test.setScoreToPass(80);


        UpdateTestRequest request = UpdateTestRequest.builder()
                .withTrainingId(employeeId)
                .withEmployeeId(trainingId)
                .withLatestScore(90)
                .build();


        when(testDao.getTest(any(String.class), any(String.class))).thenReturn(test);
        when(testDao.saveTest(any(Test.class))).thenReturn(test);

        // WHEN
        UpdateTestResult result = updateTestActivity.handleRequest(request);

        // THEN
        assertEquals(employeeId, result.getTest().getEmployeeId());
        assertTrue(result.getTest().getHasPassed());
        assertEquals(90, result.getTest().getLatestScore());
        assertFalse(result.getTest().getTestAttempts().isEmpty());
    }

}
