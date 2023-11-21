package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.UpdateTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.UpdateTrainingResult;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UpdateTrainingActivityTest {
    @Mock
    private TrainingDao trainingDao;

    @Mock
    private EmployeeDao employeeDao;

    private UpdateTrainingActivity updateTrainingActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        updateTrainingActivity = new UpdateTrainingActivity(trainingDao, employeeDao);
    }

    @Test
    public void handleRequest_goodRequest_updatesTrainingTable() {
        // GIVEN
        String trainingName = "name";
        String trainingId = "trainingId";
        String testId = "test1";
        String employeeId = "employeeId";

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

        Employee employee = new Employee();
        employee.setTrainingsTaken(new HashSet<>());


        UpdateTrainingRequest request = UpdateTrainingRequest.builder()
                .withTrainingId(trainingId)
                .withIsActive(trainingName)
                .withMonthsTilExpire(1)
                .withIsActive(String.valueOf(true))
                .withEmployeesTrained(Set.of(employeeId))
                .withTestsForTraining(Set.of(testId))
                .withExpirationStatus(String.valueOf(Status.SOON_TO_EXPIRE))
                .build();


        when(trainingDao.getTraining(any(String.class))).thenReturn(training);
        when(trainingDao.saveTraining(any(Training.class))).thenReturn(training);
        when(employeeDao.getEmployee(any(String.class))).thenReturn(employee);


        // WHEN
        UpdateTrainingResult result = updateTrainingActivity.handleRequest(request);

        // THEN
        assertEquals(trainingName, result.getTraining().getTrainingName());
        assertEquals(1, result.getTraining().getMonthsTilExpire());
        assertEquals(Status.SOON_TO_EXPIRE, result.getTraining().getExpirationStatus());
        assertFalse(result.getTraining().getEmployeesTrained().isEmpty());
        assertFalse(result.getTraining().getTestsForTraining().isEmpty());
        verify(employeeDao).saveEmployee(any(Employee.class));
    }

}
