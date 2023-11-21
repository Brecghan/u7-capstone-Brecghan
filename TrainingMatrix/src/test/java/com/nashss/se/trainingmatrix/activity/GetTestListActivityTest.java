package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetTestListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTestListResult;
import com.nashss.se.trainingmatrix.dynamodb.TestDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;
import com.nashss.se.trainingmatrix.utils.NameConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetTestListActivityTest {
    @Mock
    private TestDao testDao;

    private GetTestListActivity getTestListActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getTestListActivity = new GetTestListActivity(testDao);
    }

    @Test
    public void handleRequest_savedTestListFoundPassBoolean_returnsTestModelListInResult() {
        // GIVEN
        String expectedTrainingId = "expectedTrainingId";
        String expectedEmployeeId = "expectedEmployeeId";

        com.nashss.se.trainingmatrix.dynamodb.models.Test test = new com.nashss.se.trainingmatrix.dynamodb.models.Test();
        test.setTrainingId(expectedTrainingId);
        test.setEmployeeId(expectedEmployeeId);
        test.setHasPassed(true);
        test.setScoreToPass(80);
        test.setLatestScore(80);
        test.setTestAttempts(new ArrayList<>());

        com.nashss.se.trainingmatrix.dynamodb.models.Test test2 = new com.nashss.se.trainingmatrix.dynamodb.models.Test();
        test2.setTrainingId(expectedTrainingId);
        test2.setEmployeeId(expectedEmployeeId);
        test2.setHasPassed(true);
        test2.setScoreToPass(80);
        test2.setLatestScore(90);
        test2.setTestAttempts(new ArrayList<>());

        List<com.nashss.se.trainingmatrix.dynamodb.models.Test> testList = List.of(test, test2);

        when(testDao.getTestListPassSpecific(eq(true), any(String.class), any(String.class))).thenReturn(testList);

        GetTestListRequest request = GetTestListRequest.builder()
                .withTrainingId(expectedTrainingId)
                .withEmployeeId(expectedEmployeeId)
                .withHasPassed(String.valueOf(true))
                .build();

        // WHEN
        GetTestListResult result = getTestListActivity.handleRequest(request);

        // THEN
        assertEquals(expectedTrainingId, result.getTests().get(0).getTrainingId());
        assertEquals(expectedEmployeeId, result.getTests().get(0).getEmployeeId());
        assertEquals(80, result.getTests().get(0).getLatestScore());
        assertEquals(90, result.getTests().get(1).getLatestScore());
    }

    @Test
    public void handleRequest_savedTestListFoundPassBooleanIsNull_returnsTestModelListInResult() {
        // GIVEN
        String expectedTrainingId = "expectedTrainingId";
        String expectedEmployeeId = "expectedEmployeeId";

        com.nashss.se.trainingmatrix.dynamodb.models.Test test = new com.nashss.se.trainingmatrix.dynamodb.models.Test();
        test.setTrainingId(expectedTrainingId);
        test.setEmployeeId(expectedEmployeeId);
        test.setHasPassed(true);
        test.setScoreToPass(80);
        test.setLatestScore(80);
        test.setTestAttempts(new ArrayList<>());

        com.nashss.se.trainingmatrix.dynamodb.models.Test test2 = new com.nashss.se.trainingmatrix.dynamodb.models.Test();
        test2.setTrainingId(expectedTrainingId);
        test2.setEmployeeId(expectedEmployeeId);
        test2.setHasPassed(true);
        test2.setScoreToPass(80);
        test2.setLatestScore(90);
        test2.setTestAttempts(new ArrayList<>());

        List<com.nashss.se.trainingmatrix.dynamodb.models.Test> testList = List.of(test, test2);

        when(testDao.getTestList(any(String.class), any(String.class))).thenReturn(testList);

        GetTestListRequest request = GetTestListRequest.builder()
                .withTrainingId(expectedTrainingId)
                .withEmployeeId(expectedEmployeeId)
                .withHasPassed("null")
                .build();

        // WHEN
        GetTestListResult result = getTestListActivity.handleRequest(request);

        // THEN
        assertEquals(expectedTrainingId, result.getTests().get(0).getTrainingId());
        assertEquals(expectedEmployeeId, result.getTests().get(0).getEmployeeId());
        assertEquals(80, result.getTests().get(0).getLatestScore());
        assertEquals(90, result.getTests().get(1).getLatestScore());
    }
}
