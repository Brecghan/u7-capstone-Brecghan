package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.requests.GetTrainingSeriesListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTrainingSeriesListResult;
import com.nashss.se.trainingmatrix.dynamodb.TrainingSeriesDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.TrainingSeries;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetTrainingSeriesListActivityTest {
    @Mock
    private TrainingSeriesDao trainingSeriesDao;

    private GetTrainingSeriesListActivity getTrainingSeriesListActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getTrainingSeriesListActivity = new GetTrainingSeriesListActivity(trainingSeriesDao);
    }

    @Test
    public void handleRequest_savedTrainingSeriesListFound_returnsTrainingSeriesModelListInResult() {
        // GIVEN
        String expectedName = "expectedName";

        TrainingSeries trainingSeries = new TrainingSeries();
        trainingSeries.setTrainingSeriesName(expectedName);

        TrainingSeries trainingSeries2 = new TrainingSeries();
        trainingSeries2.setTrainingSeriesName(expectedName + "2");

        List<TrainingSeries> trainingSeriesList = List.of(trainingSeries, trainingSeries2);

        when(trainingSeriesDao.getTrainingSeriesList()).thenReturn(trainingSeriesList);

        GetTrainingSeriesListRequest request = GetTrainingSeriesListRequest.builder()
                .build();

        // WHEN
        GetTrainingSeriesListResult result = getTrainingSeriesListActivity.handleRequest(request);

        // THEN
        assertEquals(expectedName, result.getTrainingSeriesList().get(0).getTrainingSeriesName());
        assertEquals(expectedName + "2", result.getTrainingSeriesList().get(1).getTrainingSeriesName());
    }
}