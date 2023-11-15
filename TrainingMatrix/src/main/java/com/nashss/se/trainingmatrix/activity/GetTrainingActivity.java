package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTrainingResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.models.TrainingModel;

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

    /**
     * This method handles the incoming request by retrieving a training from the database.
     * <p>
     * It then returns the matching training.
     *
     * @param getTrainingRequest request object containing the Training ID
     * @return getTrainingResult result object containing the Training requested
     */
    public GetTrainingResult handleRequest(final GetTrainingRequest getTrainingRequest) {

        Training result = trainingDao.getTraining(getTrainingRequest.getTrainingId());
        TrainingModel trainingModel = new ModelConverter().toTrainingModel(result);

        return GetTrainingResult.builder()
                .withTraining(trainingModel)
                .build();
    }
}
