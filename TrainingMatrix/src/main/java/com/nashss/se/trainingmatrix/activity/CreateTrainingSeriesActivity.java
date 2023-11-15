package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.TrainingSeriesDao;

import javax.inject.Inject;

public class CreateTrainingSeriesActivity {
    private final TrainingSeriesDao trainingSeriesDao;

    /**
     * Instantiates a new CreateTrainingSeriesActivity object.
     *
     * @param trainingSeriesDao   TrainingSeriesDao to access the Training Series table.
     */
    @Inject
    public CreateTrainingSeriesActivity(TrainingSeriesDao trainingSeriesDao) {
        this.trainingSeriesDao = trainingSeriesDao;
    }
}
