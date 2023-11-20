package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.CreateTestRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateTestResult;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.TestDao;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.utils.NameConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateTestActivityTest {
    @Mock
    private TestDao testDao;
    @Mock
    private EmployeeDao employeeDao;
    @Mock
    private TrainingDao trainingDao;

    private CreateTestActivity createTestActivity;
    private NameConverter converter = new NameConverter();

    @BeforeEach
    void setUp() {
        openMocks(this);
        createTestActivity = new CreateTestActivity(testDao, employeeDao, trainingDao);
    }

    @Test
    public void handleRequest_withGoodData_createsAndSavesTest() {
        // GIVEN
        String expectedEmployeeId = "expectedId";
        List<String> expectedEmployeeIds = List.of(expectedEmployeeId);
        String expectedTrainingId = "expectedTestId";
        String expectedName = converter.testNameCreate(expectedTrainingId, expectedEmployeeId);
        Employee employee = new Employee();
        employee.setTestsTaken(new HashSet<>());
        Training training = new Training();
        training.setTestsForTraining(new HashSet<>());
        when(employeeDao.getEmployee(expectedEmployeeId)).thenReturn(employee);
        when(trainingDao.getTraining(expectedTrainingId)).thenReturn(training);

        CreateTestRequest request = CreateTestRequest.builder()
                .withEmployeeIds(expectedEmployeeIds)
                .withTrainingId(expectedTrainingId)
                .withScoreToPass(85)
                .build();

        // WHEN
        CreateTestResult result = createTestActivity.handleRequest(request);

        // THEN
        verify(testDao).saveTest(any(com.nashss.se.trainingmatrix.dynamodb.models.Test.class));
        verify(employeeDao).saveEmployee(any(Employee.class));
        verify(trainingDao).saveTraining(any(Training.class));

        assertEquals(expectedEmployeeId, result.getTests().get(0).getEmployeeId());
        assertEquals(expectedTrainingId, result.getTests().get(0).getTrainingId());
        assertEquals(expectedName, converter.testNameCreate(
                result.getTests().get(0).getTrainingId(),result.getTests().get(0).getEmployeeId()));
    }

}
