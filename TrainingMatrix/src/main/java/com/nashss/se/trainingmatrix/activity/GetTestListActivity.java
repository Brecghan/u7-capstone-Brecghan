package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetTestListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTestListResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.TestDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Test;
import com.nashss.se.trainingmatrix.models.TestModel;

import java.util.List;
import javax.inject.Inject;

public class GetTestListActivity {
    private final TestDao testDao;

    /**
     * Instantiates a new GetTestListActivity object.
     *
     * @param testDao   TestDao to access the Test table.
     */
    @Inject
    public GetTestListActivity(TestDao testDao) {
        this.testDao = testDao;
    }

    /**
     * This method handles the incoming request by retrieving List of Tests from the database.
     * <p>
     * It then returns the matching tests, or an empty result list if none are found.
     *
     * @param getTestListRequest request object which may contain the Parameters of hasPassed, TrainingId, & EmployeeId
     * @return getTestListResult result object containing the tests that matched the passed parameters
     */
    public GetTestListResult handleRequest(final GetTestListRequest getTestListRequest) {
        List<Test> results;
        if (getTestListRequest.getHasPassed() == null) {
            results = testDao.getTestList(getTestListRequest.getTrainingId(), getTestListRequest.getEmployeeId());
        } else {
            results = testDao.getTestListPassSpecific(getTestListRequest.getHasPassed(),
                    getTestListRequest.getTrainingId(), getTestListRequest.getEmployeeId());
        }
        List<TestModel> testModels = new ModelConverter().toTestModelList(results);

        return GetTestListResult.builder()
                .withTests(testModels)
                .build();
    }
}
