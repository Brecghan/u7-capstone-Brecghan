package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetTrainingListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTrainingListResult;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetTrainingListActivityTest {
    @Mock
    private TrainingDao trainingDao;

    private GetTrainingListActivity getTrainingListActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getTrainingListActivity = new GetTrainingListActivity(trainingDao);
    }

    @Test
    public void handleRequest_savedTrainingListFound_returnsTrainingModelListInResult() {
        // GIVEN
        String expectedId = "expectedId";
        String expectedName = "expectedName";
        ZonedDateTime expectedDate = ZonedDateTime.now();
        Status expectedStatus = Status.UP_TO_DATE;
        String expectedSeries = "test_Series";

        Training training = new Training();
        training.setTrainingId(expectedId);
        training.setTrainingName(expectedName);
        training.setIsActive(true);
        training.setMonthsTilExpire(9);
        training.setTrainingDate(expectedDate);
        training.setTestsForTraining(new HashSet<>());
        training.setEmployeesTrained(new HashSet<>());
        training.setExpirationStatus(expectedStatus);
        training.setTrainingSeries(expectedSeries);

        Training training2 = new Training();
        training2.setTrainingId(expectedId);
        training2.setTrainingName(expectedName);
        training2.setIsActive(false);
        training2.setMonthsTilExpire(10);
        training2.setTrainingDate(expectedDate);
        training2.setTestsForTraining(new HashSet<>());
        training2.setEmployeesTrained(new HashSet<>());
        training2.setExpirationStatus(expectedStatus);
        training2.setTrainingSeries(expectedSeries);


        List<Training> trainingList = List.of(training, training2);

        when(trainingDao.getTrainingList(true, expectedStatus, expectedSeries)).thenReturn(trainingList);

        GetTrainingListRequest request = GetTrainingListRequest.builder()
                .withStatus(String.valueOf(expectedStatus))
                .withIsActive(true)
                .withTrainingSeries(expectedSeries)
                .build();

        // WHEN
        GetTrainingListResult result = getTrainingListActivity.handleRequest(request);

        // THEN
        assertEquals(expectedId, result.getTrainings().get(0).getTrainingId());
        assertEquals(expectedName, result.getTrainings().get(0).getTrainingName());
        assertEquals(expectedStatus, result.getTrainings().get(0).getExpirationStatus());
        assertEquals(String.valueOf(expectedDate), result.getTrainings().get(0).getTrainingDate());
        assertEquals(9, result.getTrainings().get(0).getMonthsTilExpire());
        assertEquals(10, result.getTrainings().get(1).getMonthsTilExpire());
    }
}