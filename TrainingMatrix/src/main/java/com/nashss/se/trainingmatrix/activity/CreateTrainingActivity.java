package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;

import javax.inject.Inject;

public class CreateTrainingActivity {
    private final TrainingDao trainingDao;

    /**
     * Instantiates a new CreateTrainingActivity object.
     *
     * @param trainingDao   TrainingDao to access the Training table.
     */
    @Inject
    public CreateTrainingActivity(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }
}
