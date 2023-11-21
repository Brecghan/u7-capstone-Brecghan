package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.CreateTrainingSeriesRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateTrainingSeriesResult;
import com.nashss.se.trainingmatrix.dynamodb.TrainingSeriesDao;
import com.nashss.se.trainingmatrix.dynamodb.models.TrainingSeries;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateTrainingSeriesActivityTest {
    @Mock
    private TrainingSeriesDao trainingSeriesDao;

    private CreateTrainingSeriesActivity createTrainingSeriesActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createTrainingSeriesActivity = new CreateTrainingSeriesActivity(trainingSeriesDao);
    }

    @Test
    public void handleRequest_withGoodData_createsAndSavesTrainingSeries() {
        // GIVEN
        String expectedName = "expectedName";

        CreateTrainingSeriesRequest request = CreateTrainingSeriesRequest.builder()
                .withTrainingSeriesName(expectedName)
                .build();

        // WHEN
        CreateTrainingSeriesResult result = createTrainingSeriesActivity.handleRequest(request);

        // THEN
        verify(trainingSeriesDao).saveTrainingSeries(any(TrainingSeries.class));

        assertEquals(expectedName, result.getTrainingSeries().getTrainingSeriesName());
    }

}
