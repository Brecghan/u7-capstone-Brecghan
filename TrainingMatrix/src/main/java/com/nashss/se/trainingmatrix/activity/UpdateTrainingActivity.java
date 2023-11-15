package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;

import javax.inject.Inject;

public class UpdateTrainingActivity {
    private final TrainingDao trainingDao;

    /**
     * Instantiates a new UpdateTrainingActivity object.
     *
     * @param trainingDao   TrainingDao to access the Training table.
     */
    @Inject
    public UpdateTrainingActivity(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }
}
