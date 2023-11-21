package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.CreateTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateTrainingResult;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateTrainingActivityTest {
    @Mock
    private TrainingDao trainingDao;

    private CreateTrainingActivity createTrainingActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createTrainingActivity = new CreateTrainingActivity(trainingDao);
    }

    @Test
    public void handleRequest_withGoodDataAndNoTrainingSeries_createsAndSavesTrainingWithCorrectId() {
        // GIVEN
        String expectedName = "expectedName";
        String trainingDate = String.valueOf(ZonedDateTime.now());
        String expectedTrainingId = expectedName + ":" + trainingDate.substring(0, 10);

        CreateTrainingRequest request = CreateTrainingRequest.builder()
                .withTrainingName(expectedName)
                .withTrainingSeries("null")
                .withMonthsTilExpire(4)
                .withTrainingDate(trainingDate)
                .build();

        // WHEN
        CreateTrainingResult result = createTrainingActivity.handleRequest(request);

        // THEN
        verify(trainingDao).saveTraining(any(Training.class));

        assertEquals(expectedName, result.getTraining().getTrainingName());
        assertEquals(expectedTrainingId, result.getTraining().getTrainingId());
    }

    @Test
    public void handleRequest_withGoodDataAndTrainingSeries_createsAndSavesTrainingWithCorrectId() {
        // GIVEN
        String expectedName = "expectedName";
        String trainingDate = String.valueOf(ZonedDateTime.now());
        String trainingSeries = "Idea_Gen";
        String expectedTrainingId = trainingSeries + ":" + trainingDate;

        CreateTrainingRequest request = CreateTrainingRequest.builder()
                .withTrainingName(expectedName)
                .withTrainingSeries(trainingSeries)
                .withMonthsTilExpire(4)
                .withTrainingDate(trainingDate)
                .build();

        // WHEN
        CreateTrainingResult result = createTrainingActivity.handleRequest(request);

        // THEN
        verify(trainingDao).saveTraining(any(Training.class));

        assertEquals(expectedName, result.getTraining().getTrainingName());
        assertEquals(expectedTrainingId, result.getTraining().getTrainingId());
    }

}
