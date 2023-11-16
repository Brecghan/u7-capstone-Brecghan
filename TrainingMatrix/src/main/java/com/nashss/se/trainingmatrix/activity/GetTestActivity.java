package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.GetTestRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTestResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.TestDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Test;
import com.nashss.se.trainingmatrix.models.TestModel;
import com.nashss.se.trainingmatrix.utils.NameConverter;

import javax.inject.Inject;

public class GetTestActivity {

    private static final NameConverter converter = new NameConverter();
    private final TestDao testDao;

    /**
     * Instantiates a new GetTestActivity object.
     *
     * @param testDao   TestDao to access the Test table.
     */
    @Inject
    public GetTestActivity(TestDao testDao) {
        this.testDao = testDao;
    }

    /**
     * This method handles the incoming request by retrieving an test from the database.
     * <p>
     * It then returns the matching test.
     *
     * @param getTestRequest request object containing the Test ID which splits to the Employee ID & Training ID
     * @return getTestResult result object containing the Test requested
     */
    public GetTestResult handleRequest(final GetTestRequest getTestRequest) {
        String[] testName = converter.testNameSplit(getTestRequest.getTestId());
        Test result = testDao.getTest(testName[0], testName[1]);
        TestModel testModel = new ModelConverter().toTestModel(result);

        return GetTestResult.builder()
                .withTest(testModel)
                .build();
    }
}
