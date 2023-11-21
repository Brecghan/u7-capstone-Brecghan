package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.DeleteTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.DeleteTrainingResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.models.TrainingModel;

import javax.inject.Inject;

public class DeleteTrainingActivity {
    private final TrainingDao trainingDao;

    /**
     * Instantiates a new DeleteTrainingActivity object.
     *
     * @param trainingDao   TrainingDao to access the Trainings table.
     */
    @Inject
    public DeleteTrainingActivity(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    /**
     * This method handles the incoming request by retrieving a training using the training ID and updating
     * the isActive variable for that training to false.
     * <p>
     * It then returns the deleted training.
     * <p>
     *
     * @param deleteTrainingRequest request object containing the training ID
     * @return deleteTrainingResult result object containing the API defined {@link TrainingModel}
     */
    public DeleteTrainingResult handleRequest(final DeleteTrainingRequest deleteTrainingRequest) {
        Training training = trainingDao.getTraining(deleteTrainingRequest.getTrainingId());

        training.setIsActive(false);

        trainingDao.saveTraining(training);

        TrainingModel trainingModel = new ModelConverter().toTrainingModel(training);
        return DeleteTrainingResult.builder()
                .withTraining(trainingModel)
                .build();
    }
}
