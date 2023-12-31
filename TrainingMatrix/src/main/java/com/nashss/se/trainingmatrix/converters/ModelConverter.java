package com.nashss.se.trainingmatrix.converters;

import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.Test;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.dynamodb.models.TrainingSeries;
import com.nashss.se.trainingmatrix.models.EmployeeModel;
import com.nashss.se.trainingmatrix.models.TestModel;
import com.nashss.se.trainingmatrix.models.TrainingModel;
import com.nashss.se.trainingmatrix.models.TrainingSeriesModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {
    /**
     * Converts a provided {@link Employee} into an {@link EmployeeModel} representation.
     *
     * @param employee the employee to convert
     * @return the converted employee
     */
    public EmployeeModel toEmployeeModel(Employee employee) {
        Set<String> testsTaken = null;
        Set<String> trainingsTaken = null;
        if (employee.getTestsTaken() != null) {
            testsTaken = new HashSet<>(employee.getTestsTaken());
        }
        if (employee.getTrainingsTaken() != null) {
            trainingsTaken = new HashSet<>(employee.getTrainingsTaken());
        }

        return EmployeeModel.builder()
                .withEmployeeId(employee.getEmployeeId())
                .withEmployeeName(employee.getEmployeeName())
                .withIsActive(employee.getIsActive())
                .withTeam(employee.getTeam())
                .withStartDate(employee.getStartDate())
                .withTrainingsTaken(trainingsTaken)
                .withTestsTaken(testsTaken)
                .withTrainingStatus(employee.getTrainingStatus())
                .build();
    }

    /**
     * Converts a list of Employees to a list of EmployeeModels.
     *
     * @param employees The Employees to convert to EmployeeModels
     * @return The converted list of EmployeeModels
     */
    public List<EmployeeModel> toEmployeeModelList(List<Employee> employees) {
        List<EmployeeModel> employeeModels = new ArrayList<>();

        for (Employee employee : employees) {
            employeeModels.add(toEmployeeModel(employee));
        }

        return employeeModels;
    }

    /**
     * Converts a provided {@link Training} into an {@link TrainingModel} representation.
     *
     * @param training the Training to convert
     * @return the converted Training
     */
    public TrainingModel toTrainingModel(Training training) {
        Set<String> employeesTrained = null;
        Set<String> testsForTraining = null;
        if (training.getEmployeesTrained() != null) {
            employeesTrained = new HashSet<>(training.getEmployeesTrained());
        }
        if (training.getTestsForTraining() != null) {
            testsForTraining = new HashSet<>(training.getTestsForTraining());
        }

        return TrainingModel.builder()
                .withTrainingId(training.getTrainingId())
                .withTrainingName(training.getTrainingName())
                .withIsActive(training.getIsActive())
                .withMonthsTilExpire(training.getMonthsTilExpire())
                .withTrainingDate(training.getTrainingDate())
                .withEmployeesTrained(employeesTrained)
                .withTestsForTraining(testsForTraining)
                .withExpirationStatus(training.getExpirationStatus())
                .withTrainingSeries(training.getTrainingSeries())
                .build();
    }

    /**
     * Converts a list of Trainings to a list of TrainingModels.
     *
     * @param trainings The Trainings to convert to TrainingModels
     * @return The converted list of TrainingModels
     */
    public List<TrainingModel> toTrainingModelList(List<Training> trainings) {
        List<TrainingModel> trainingModels = new ArrayList<>();

        for (Training training : trainings) {
            trainingModels.add(toTrainingModel(training));
        }

        return trainingModels;
    }

    /**
     * Converts a provided {@link Test} into an {@link TestModel} representation.
     *
     * @param test the Test to convert
     * @return the converted Test
     */
    public TestModel toTestModel(Test test) {
        List<String> testAttempts = null;
        if (test.getTestAttempts() != null) {
            testAttempts = new ArrayList<>(test.getTestAttempts());
        }

        return TestModel.builder()
                .withTrainingId(test.getTrainingId())
                .withEmployeeId(test.getEmployeeId())
                .withHasPassed(test.getHasPassed())
                .withScoreToPass(test.getScoreToPass())
                .withLatestScore(test.getLatestScore())
                .withTestAttempts(testAttempts)
                .build();
    }

    /**
     * Converts a list of Tests to a list of TestModels.
     *
     * @param tests The Tests to convert to TestModels
     * @return The converted list of TestModels
     */
    public List<TestModel> toTestModelList(List<Test> tests) {
        List<TestModel> testModels = new ArrayList<>();

        for (Test test : tests) {
            testModels.add(toTestModel(test));
        }

        return testModels;
    }

    /**
     * Converts a provided {@link TrainingSeries} into an {@link TrainingSeriesModel} representation.
     *
     * @param trainingSeries the trainingSeries to convert
     * @return the converted trainingSeries
     */
    public TrainingSeriesModel toTrainingSeriesModel(TrainingSeries trainingSeries) {

        return TrainingSeriesModel.builder()
                .withTrainingSeriesName(trainingSeries.getTrainingSeriesName())
                .build();
    }

    /**
     * Converts a list of TrainingSeriess to a list of TrainingSeriesModels.
     *
     * @param trainingSeriesList The TrainingSeries to convert to TrainingSeriesModels
     * @return The converted list of TrainingSeriesModels
     */
    public List<TrainingSeriesModel> toTrainingSeriesModelList(List<TrainingSeries> trainingSeriesList) {
        List<TrainingSeriesModel> trainingSeriesModels = new ArrayList<>();

        for (TrainingSeries trainingSeries : trainingSeriesList) {
            trainingSeriesModels.add(toTrainingSeriesModel(trainingSeries));
        }

        return trainingSeriesModels;
    }
}
