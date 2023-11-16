package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetTrainingSeriesListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTrainingSeriesListResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.TrainingSeriesDao;
import com.nashss.se.trainingmatrix.dynamodb.models.TrainingSeries;
import com.nashss.se.trainingmatrix.models.TrainingSeriesModel;

import java.util.List;
import javax.inject.Inject;

public class GetTrainingSeriesListActivity {
    private final TrainingSeriesDao trainingSeriesDao;

    /**
     * Instantiates a new GetTrainingSeriesListActivity object.
     *
     * @param trainingSeriesDao   TrainingSeriesDao to access the Training Series table.
     */
    @Inject
    public GetTrainingSeriesListActivity(TrainingSeriesDao trainingSeriesDao) {
        this.trainingSeriesDao = trainingSeriesDao;
    }

    /**
     * This method handles the incoming request by retrieving List of TrainingSeries from the database.
     * <p>
     * It then returns the matching TrainingSeries, or an empty result list if none are found.
     *
     * @param getTrainingSeriesListRequest request object containing the Parameters of isActive & Team
     * @return getTrainingSeriesListResult result object containing the TrainingSeries
     * that matched the passed parameters
     */
    public GetTrainingSeriesListResult handleRequest(final GetTrainingSeriesListRequest getTrainingSeriesListRequest) {

        List<TrainingSeries> results = trainingSeriesDao.getTrainingSeriesList();
        List<TrainingSeriesModel> trainingSeriesModels = new ModelConverter().toTrainingSeriesModelList(results);

        return GetTrainingSeriesListResult.builder()
                .withTrainingSeriesList(trainingSeriesModels)
                .build();
    }
}
