package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.TrainingSeriesDao;

import javax.inject.Inject;

public class GetTrainingSeriesActivity {
    private final TrainingSeriesDao trainingSeriesDao;

    /**
     * Instantiates a new GetTrainingSeriesActivity object.
     *
     * @param trainingSeriesDao   TrainingSeriesDao to access the Training Series table.
     */
    @Inject
    public GetTrainingSeriesActivity(TrainingSeriesDao trainingSeriesDao) {
        this.trainingSeriesDao = trainingSeriesDao;
    }
}
