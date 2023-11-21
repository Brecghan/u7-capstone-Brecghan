package com.nashss.se.trainingmatrix.dynamodb;

import com.nashss.se.trainingmatrix.dynamodb.models.TrainingSeries;

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

class TrainingSeriesDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private TrainingSeriesDao trainingSeriesDao;

    @Mock
    PaginatedScanList<TrainingSeries> paginatedScanList;

    @BeforeEach
    public void setup() {
        initMocks(this);
        trainingSeriesDao = new TrainingSeriesDao(dynamoDBMapper);
    }


    @Test
    public void saveTrainingSeries_callsMapperWithTrainingSeries() {
        // GIVEN
        TrainingSeries trainingSeries = new TrainingSeries();

        // WHEN
        TrainingSeries result = trainingSeriesDao.saveTrainingSeries(trainingSeries);

        // THEN
        verify(dynamoDBMapper).save(trainingSeries);
        assertEquals(trainingSeries, result);
    }

    @Test
    public void getTrainingSeriesList_withNoParametersNeeded_callsMapperScanForWholeTable() {
        // GIVEN
        when(dynamoDBMapper.scan(eq(TrainingSeries.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanList);

        // WHEN
        List<TrainingSeries> trainingSeries = trainingSeriesDao.getTrainingSeriesList();

        // THEN
        assertNotNull(trainingSeries);
        verify(dynamoDBMapper).scan(eq(TrainingSeries.class), any(DynamoDBScanExpression.class));
    }

}
