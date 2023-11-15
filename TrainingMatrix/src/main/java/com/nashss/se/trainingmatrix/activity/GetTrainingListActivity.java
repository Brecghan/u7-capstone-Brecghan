package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;

import javax.inject.Inject;

public class GetTrainingListActivity {
    private final TrainingDao trainingDao;

    /**
     * Instantiates a new GetTrainingListActivity object.
     *
     * @param trainingDao   TrainingDao to access the Training table.
     */
    @Inject
    public GetTrainingListActivity(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }
}
