package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.CreateTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateTrainingResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Status;
import com.nashss.se.trainingmatrix.models.TrainingModel;

import java.time.ZonedDateTime;
import java.util.HashSet;
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

    /**
     * This method handles the incoming request by persisting a new training
     * with the provided training name, monthsTilExpire, start date, and trainingSeries.
     * <p>
     * It then returns the newly created training.
     * <p>
     *
     * @param createTrainingRequest request object containing the training name, monthsTilExpire,
     *                              start date, and trainingSeries associated with it
     * @return createTrainingResult result object containing the API defined {@link TrainingModel}
     */
    public CreateTrainingResult handleRequest(final CreateTrainingRequest createTrainingRequest) {
        Training newTraining = new Training();
        newTraining.setTrainingName(createTrainingRequest.getTrainingName());
        if (createTrainingRequest.getTrainingSeries().equals("null")) {
            newTraining.setTrainingId(createTrainingRequest.getTrainingName() + ":" +
                    createTrainingRequest.getTrainingDate().substring(0, 10));
        } else {
            newTraining.setTrainingId(createTrainingRequest.getTrainingSeries() +
                    ":" + createTrainingRequest.getTrainingDate().substring(0,16));
        }
        newTraining.setIsActive(true);
        newTraining.setMonthsTilExpire(createTrainingRequest.getMonthsTilExpire());
        newTraining.setTrainingDate(ZonedDateTime.parse(createTrainingRequest.getTrainingDate()));
        newTraining.setTestsForTraining(new HashSet<>());
        newTraining.setEmployeesTrained(new HashSet<>());
        newTraining.setExpirationStatus(Status.UP_TO_DATE);
        newTraining.setTrainingSeries(createTrainingRequest.getTrainingSeries());

        trainingDao.saveTraining(newTraining);

        TrainingModel trainingModel = new ModelConverter().toTrainingModel(newTraining);
        return CreateTrainingResult.builder()
                .withTraining(trainingModel)
                .build();
    }
}
