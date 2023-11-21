package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTrainingResult;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetTrainingActivityTest {
    @Mock
    private TrainingDao trainingDao;

    private GetTrainingActivity getTrainingActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getTrainingActivity = new GetTrainingActivity(trainingDao);
    }

    @Test
    public void handleRequest_savedTrainingFound_returnsTrainingModelInResult() {
        // GIVEN
        String expectedId = "expectedId";
        String expectedName = "expectedName";
        Integer expectedMonths = 9;
        ZonedDateTime expectedDate = ZonedDateTime.now();
        Status expectedStatus = Status.UP_TO_DATE;
        String expectedSeries = "expectedSeriesName";

        Training training = new Training();
        training.setTrainingId(expectedId);
        training.setTrainingName(expectedName);
        training.setIsActive(true);
        training.setMonthsTilExpire(expectedMonths);
        training.setTrainingDate(expectedDate);
        training.setTestsForTraining(new HashSet<>());
        training.setEmployeesTrained(new HashSet<>());
        training.setExpirationStatus(expectedStatus);
        training.setTrainingSeries(expectedSeries);

        when(trainingDao.getTraining(expectedId)).thenReturn(training);

        GetTrainingRequest request = GetTrainingRequest.builder()
            .withTrainingId(expectedId)
            .build();

        // WHEN
        GetTrainingResult result = getTrainingActivity.handleRequest(request);

        // THEN
        assertEquals(expectedId, result.getTraining().getTrainingId());
        assertEquals(expectedName, result.getTraining().getTrainingName());
        assertEquals(expectedMonths, result.getTraining().getMonthsTilExpire());
        assertEquals(expectedStatus, result.getTraining().getExpirationStatus());
        assertEquals(String.valueOf(expectedDate), result.getTraining().getTrainingDate());
        assertEquals(expectedSeries, result.getTraining().getTrainingSeries());
    }
}