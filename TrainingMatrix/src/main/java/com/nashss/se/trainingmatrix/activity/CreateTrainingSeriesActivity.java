package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.CreateTrainingSeriesRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateTrainingSeriesResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.TrainingSeriesDao;
import com.nashss.se.trainingmatrix.dynamodb.models.TrainingSeries;
import com.nashss.se.trainingmatrix.models.TrainingSeriesModel;

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

    /**
     * This method handles the incoming request by persisting a new trainingSeries
     * with the provided trainingSeries name, trainingSeries ID, start date, and team.
     * <p>
     * It then returns the newly created trainingSeries.
     * <p>
     *
     * @param createTrainingSeriesRequest request object containing the trainingSeries name,
     *                                    trainingSeries ID, start date, and team associated with it
     * @return createTrainingSeriesResult result object containing the API defined {@link TrainingSeriesModel}
     */
    public CreateTrainingSeriesResult handleRequest(final CreateTrainingSeriesRequest createTrainingSeriesRequest) {
        TrainingSeries newTrainingSeries = new TrainingSeries();
        newTrainingSeries.setTrainingSeriesName(createTrainingSeriesRequest.getTrainingSeriesName());

        trainingSeriesDao.saveTrainingSeries(newTrainingSeries);

        TrainingSeriesModel trainingSeriesModel = new ModelConverter().toTrainingSeriesModel(newTrainingSeries);
        return CreateTrainingSeriesResult.builder()
                .withTrainingSeries(trainingSeriesModel)
                .build();
    }
}
