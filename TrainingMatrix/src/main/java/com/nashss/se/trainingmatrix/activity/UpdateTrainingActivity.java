package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.UpdateTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.UpdateTrainingResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.models.TrainingModel;

import javax.inject.Inject;
import java.util.Set;

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

    /**
     * This method handles the incoming request by retrieving a training using the
     * provided training ID and updating the isActive, monthsTilExpire, expirationStatus, testsForTraining, and employeesTrained.
     * <p>
     * It then returns the newly updated training.
     * <p>
     *
     * @param updateTrainingRequest request object containing the training ID, sActive, monthsTilExpire,
     *                              expirationStatus, testsForTraining, and employeesTrained associated with it
     * @return updateTrainingResult result object containing the API defined {@link TrainingModel}
     */
    public UpdateTrainingResult handleRequest(final UpdateTrainingRequest updateTrainingRequest) {
        Training training = trainingDao.getTraining(updateTrainingRequest.getTrainingId());

        if (updateTrainingRequest.getMonthsTilExpire()!=null) { training.setMonthsTilExpire(updateTrainingRequest.getMonthsTilExpire()); }
        if (updateTrainingRequest.getIsActive()!=null) { training.setIsActive(updateTrainingRequest.getIsActive()); }
        if (updateTrainingRequest.getExpirationStatus()!=null) { training.setExpirationStatus(updateTrainingRequest.getExpirationStatus()); }

        Set<String> updateTests = training.getTestsForTraining();
        updateTests.addAll(updateTrainingRequest.getTestsForTraining());
        training.setTestsForTraining(updateTests);

        Set<String> updateEmployees = training.getEmployeesTrained();
        updateEmployees.addAll(updateTrainingRequest.getEmployeesTrained());
        training.setEmployeesTrained(updateEmployees);

        trainingDao.saveTraining(training);

        TrainingModel trainingModel = new ModelConverter().toTrainingModel(training);
        return UpdateTrainingResult.builder()
                .withTraining(trainingModel)
                .build();
    }
}
