package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetTestRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTestResult;
import com.nashss.se.trainingmatrix.dynamodb.TestDao;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;
import com.nashss.se.trainingmatrix.utils.NameConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetTestActivityTest {
    @Mock
    private TestDao testDao;

    private GetTestActivity getTestActivity;

    private NameConverter converter = new NameConverter();

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getTestActivity = new GetTestActivity(testDao);
    }

    @Test
    public void handleRequest_savedTestFound_returnsTestModelInResult() {
        // GIVEN
        String expectedTrainingId = "expectedTrainingId";
        String expectedEmployeeId = "expectedEmployeeId";
        String expectedTestId = converter.testNameCreate(expectedTrainingId, expectedEmployeeId);

        com.nashss.se.trainingmatrix.dynamodb.models.Test test = new com.nashss.se.trainingmatrix.dynamodb.models.Test();
        test.setTrainingId(expectedTrainingId);
        test.setEmployeeId(expectedEmployeeId);
        test.setHasPassed(true);
        test.setScoreToPass(80);
        test.setLatestScore(80);
        test.setTestAttempts(new ArrayList<>());

        when(testDao.getTest(expectedTrainingId, expectedEmployeeId)).thenReturn(test);

        GetTestRequest request = GetTestRequest.builder()
            .withTestId(expectedTestId)
            .build();

        // WHEN
        GetTestResult result = getTestActivity.handleRequest(request);

        // THEN
        assertEquals(expectedTrainingId, result.getTest().getTrainingId());
        assertEquals(expectedEmployeeId, result.getTest().getEmployeeId());
        assertEquals(true, result.getTest().getHasPassed());
        assertEquals(80, result.getTest().getLatestScore());
        assertEquals(80, result.getTest().getScoreToPass());
    }
}