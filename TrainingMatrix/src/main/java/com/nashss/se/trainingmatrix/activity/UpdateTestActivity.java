package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.UpdateTestRequest;
import com.nashss.se.trainingmatrix.activity.results.UpdateTestResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.TestDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Test;
import com.nashss.se.trainingmatrix.models.TestModel;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

public class UpdateTestActivity {
    private final TestDao testDao;

    /**
     * Instantiates a new UpdateTestActivity object.
     *
     * @param testDao   TestDao to access the Test table.
     */
    @Inject
    public UpdateTestActivity(TestDao testDao) {
        this.testDao = testDao;
    }

    /**
     * This method handles the incoming request by retrieving a test using the trainingId and employeeId
     * and updating it with the latestScore.
     * It then verifies if hasPassed, and adds the attempt to testAttempts
     * <p>
     * It then returns the newly updated test.
     * <p>
     *
     * @param updateTestRequest request object containing the test name, test ID, start date, team, 
     *                              isActive, trainingStatus, and trainingsTaken associated with it
     * @return updateTestResult result object containing the API defined {@link TestModel}
     */
    public UpdateTestResult handleRequest(final UpdateTestRequest updateTestRequest) {
        Test test = testDao.getTest(updateTestRequest.getTrainingId(), updateTestRequest.getEmployeeId());
        test.setLatestScore(updateTestRequest.getLatestScore());
        if (test.getLatestScore() >= test.getScoreToPass()) {
            test.setHasPassed(true);
        }
        List<String> updateAttempts = test.getTestAttempts();
        int attemptNumber = updateAttempts.size()+1;
        updateAttempts.add("Attempt " + attemptNumber + " had a score of: " + test.getLatestScore());
        test.setTestAttempts(updateAttempts);
        testDao.saveTest(test);

        TestModel testModel = new ModelConverter().toTestModel(test);
        return UpdateTestResult.builder()
                .withTest(testModel)
                .build();
    }
}
