package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetTrainingListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTrainingListResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.models.TrainingModel;

import javax.inject.Inject;
import java.util.List;

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


    /**
     * This method handles the incoming request by retrieving List of Trainings from the database.
     * <p>
     * It then returns the matching trainings, or an empty result list if none are found.
     *
     * @param getTrainingListRequest request object containing the Parameters of isActive & Team
     * @return getTrainingListResult result object containing the trainings that matched the passed parameters
     */
    public GetTrainingListResult handleRequest(final GetTrainingListRequest getTrainingListRequest) {

        List<Training> results = trainingDao.getTrainingList(getTrainingListRequest.getIsActive(),
                getTrainingListRequest.getStatus(), getTrainingListRequest.getTrainingSeries());
        List<TrainingModel> trainingModels = new ModelConverter().toTrainingModelList(results);

        return GetTrainingListResult.builder()
                .withTrainings(trainingModels)
                .build();
    }
}
