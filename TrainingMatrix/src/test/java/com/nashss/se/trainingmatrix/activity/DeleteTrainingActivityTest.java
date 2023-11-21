package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.DeleteTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.DeleteTrainingResult;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class DeleteTrainingActivityTest {
    @Mock
    private TrainingDao trainingDao;

    @Mock
    private EmployeeDao employeeDao;

    private DeleteTrainingActivity deleteTrainingActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        deleteTrainingActivity = new DeleteTrainingActivity(trainingDao);
    }

    @Test
    public void handleRequest_goodRequest_setsTrainingToInactive() {
        // GIVEN
        String trainingName = "name";
        String trainingId = "trainingId";

        Training training = new Training();
        training.setTrainingId(trainingId);
        training.setTrainingName(trainingName);
        training.setIsActive(true);
        training.setMonthsTilExpire(6);
        training.setEmployeesTrained(new HashSet<>());
        training.setTestsForTraining(new HashSet<>());
        training.setExpirationStatus(Status.UP_TO_DATE);
        training.setTrainingDate(ZonedDateTime.now());
        training.setTrainingSeries(null);

        DeleteTrainingRequest request = DeleteTrainingRequest.builder()
                .withTrainingId(trainingId)
                .build();

        when(trainingDao.getTraining(any(String.class))).thenReturn(training);
        when(trainingDao.saveTraining(any(Training.class))).thenReturn(training);

        // WHEN
        DeleteTrainingResult result = deleteTrainingActivity.handleRequest(request);

        // THEN
        assertEquals(trainingId, result.getTraining().getTrainingId());
        assertFalse(result.getTraining().getActive());
    }

}
