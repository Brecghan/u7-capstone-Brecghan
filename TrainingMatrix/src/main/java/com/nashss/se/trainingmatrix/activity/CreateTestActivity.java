package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.CreateTestRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateTestResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.TestDao;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.Test;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.models.TestModel;
import com.nashss.se.trainingmatrix.utils.NameConverter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

public class CreateTestActivity {
    private final TestDao testDao;
    private final EmployeeDao employeeDao;
    private final TrainingDao trainingDao;
    private final NameConverter converter = new NameConverter();

    /**
     * Instantiates a new CreateTestActivity object.
     *
     * @param testDao   TestDao to access the Test table.
     * @param employeeDao   EmployeeDao to access the Employee table.
     * @param trainingDao   TrainingDao to access the Training table.
     */
    @Inject
    public CreateTestActivity(TestDao testDao, EmployeeDao employeeDao, TrainingDao trainingDao) {
        this.testDao = testDao;
        this.employeeDao = employeeDao;
        this.trainingDao = trainingDao;
    }

    /**
     * This method handles the incoming request by persisting new tests
     * with the provided training ID, employee IDs, and scoreToPass.
     * <p>
     * It then returns the newly created test.
     * <p>
     *
     * @param createTestRequest request object containing training ID, employee IDs, and scoreToPass
     *                              associated with it
     * @return createTestResult result object containing the API defined {@link TestModel}
     */
    public CreateTestResult handleRequest(final CreateTestRequest createTestRequest) {
        List<Test> createdTests = new ArrayList<>();
        Set<String> createdTestNames = new HashSet<>();
        for (String employee : createTestRequest.getEmployeeIds()) {
            Test newTest = new Test();
            newTest.setTrainingId(createTestRequest.getTrainingId());
            newTest.setEmployeeId(employee);
            newTest.setHasPassed(false);
            newTest.setScoreToPass(createTestRequest.getScoreToPass());
            newTest.setLatestScore(null);
            newTest.setTestAttempts(new ArrayList<>());

            String testName = converter.testNameCreate(createTestRequest.getTrainingId(), employee);
            addTestToEmployee(employee, testName);
            createdTestNames.add(testName);
            testDao.saveTest(newTest);
            createdTests.add(newTest);
        }

        addTestsToTraining(createdTestNames, createTestRequest.getTrainingId());
        List<TestModel> testModels = new ModelConverter().toTestModelList(createdTests);
        return CreateTestResult.builder()
                .withTests(testModels)
                .build();
    }


    /**
     * This method updates the Employees that the test have been created for.
     * <p>
     *
     * @param employeeId The employeeId that the test was created for
     * @param testName The testName for the test
     */
    private void addTestToEmployee(String employeeId, String testName) {
        Employee employee = employeeDao.getEmployee(employeeId);
        Set<String> testsTaken = employee.getTestsTaken();
        testsTaken.add(testName);
        employee.setTestsTaken(testsTaken);
        employeeDao.saveEmployee(employee);
    }

    /**
     * This method updates the training that the tests were created for.
     * <p>
     *
     * @param createdTests The List of Tests added to the training
     * @param trainingId The training ID the test were created for
     */
    private void addTestsToTraining(Set<String> createdTests, String trainingId) {
        Training training = trainingDao.getTraining(trainingId);
        Set<String> testsForTraining = training.getTestsForTraining();
        testsForTraining.addAll(createdTests);
        training.setTestsForTraining(testsForTraining);
        trainingDao.saveTraining(training);
    }
}
