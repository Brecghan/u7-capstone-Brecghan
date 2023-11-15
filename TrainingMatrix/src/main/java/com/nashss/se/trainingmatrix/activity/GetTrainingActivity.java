package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;

import javax.inject.Inject;

public class GetTrainingActivity {
    private final TrainingDao trainingDao;

    /**
     * Instantiates a new GetTrainingActivity object.
     *
     * @param trainingDao   TrainingDao to access the Training table.
     */
    @Inject
    public GetTrainingActivity(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }
}
