package com.nashss.se.trainingmatrix.dynamodb;

import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;
import com.nashss.se.trainingmatrix.exceptions.TrainingNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class TrainingDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private TrainingDao trainingDao;

    @Mock
    PaginatedScanList<Training> paginatedScanList;

    @Mock
    PaginatedQueryList<Training> paginatedQueryList;

    @Captor
    ArgumentCaptor<DynamoDBScanExpression> scanCaptor;

    @Captor
    ArgumentCaptor<DynamoDBQueryExpression<Training>> queryCaptor;

    @BeforeEach
    public void setup() {
        initMocks(this);
        trainingDao = new TrainingDao(dynamoDBMapper);
    }

    @Test
    public void getTraining_withTrainingId_callsMapperWithPartitionKey() {
        // GIVEN
        String trainingId = "trainingId";
        when(dynamoDBMapper.load(Training.class, trainingId)).thenReturn(new Training());

        // WHEN
        Training training = trainingDao.getTraining(trainingId);

        // THEN
        assertNotNull(training);
        verify(dynamoDBMapper).load(Training.class, trainingId);
    }

    @Test
    public void getTraining_trainingIdNotFound_throwsTrainingNotFoundException() {
        // GIVEN
        String nonexistentTrainingId = "NotReal";
        when(dynamoDBMapper.load(Training.class, nonexistentTrainingId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(TrainingNotFoundException.class, () -> trainingDao.getTraining(nonexistentTrainingId));
    }

    @Test
    public void saveTraining_callsMapperWithTraining() {
        // GIVEN
        Training training = new Training();

        // WHEN
        Training result = trainingDao.saveTraining(training);

        // THEN
        verify(dynamoDBMapper).save(training);
        assertEquals(training, result);
    }

    @Test
    public void getTrainingList_withNoParameters_callsMapperScan() {
        // GIVEN
        when(dynamoDBMapper.scan(eq(Training.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanList);

        // WHEN
        List<Training> trainings = trainingDao.getTrainingList(false, null, null);

        // THEN
        assertNotNull(trainings);
        verify(dynamoDBMapper).scan(eq(Training.class), any(DynamoDBScanExpression.class));
    }

    @Test
    public void getTrainingList_withActiveParameter_callsMapperScan() {
        // GIVEN
        when(dynamoDBMapper.scan(eq(Training.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanList);

        // WHEN
        List<Training> trainings = trainingDao.getTrainingList(true, null, null);

        // THEN
        verify(dynamoDBMapper).scan(eq(Training.class), scanCaptor.capture());
        assertNotNull(trainings);
        verify(dynamoDBMapper).scan(eq(Training.class), any(DynamoDBScanExpression.class));
        assertFalse(scanCaptor.getValue().getFilterExpression().isEmpty());
    }

    @Test
    public void getTrainingList_withActiveParameterAndStatus_callsMapperScan() {
        // GIVEN
        Status status = Status.UP_TO_DATE;
        when(dynamoDBMapper.scan(eq(Training.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanList);

        // WHEN
        List<Training> trainings = trainingDao.getTrainingList(true, status, null);

        // THEN
        verify(dynamoDBMapper).scan(eq(Training.class), scanCaptor.capture());
        assertNotNull(trainings);
        verify(dynamoDBMapper).scan(eq(Training.class), any(DynamoDBScanExpression.class));
        assertFalse(scanCaptor.getValue().getFilterExpression().isEmpty());
    }

    @Test
    public void getTrainingList_withTrainingSeries_callsGSIMapperQuery() {
        // GIVEN
        String trainingSeries = "trainingSeries";
        when(dynamoDBMapper.query(eq(Training.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        // WHEN
        List<Training> trainings = trainingDao.getTrainingList(true, null, trainingSeries);

        // THEN
        verify(dynamoDBMapper).query(eq(Training.class), queryCaptor.capture());
        assertNotNull(trainings);
        verify(dynamoDBMapper).query(eq(Training.class), any(DynamoDBQueryExpression.class));
        assertFalse(queryCaptor.getValue().getKeyConditionExpression().isEmpty());
        assertEquals(Training.TRAININGS_BY_SERIES_INDEX, queryCaptor.getValue().getIndexName());
    }
}