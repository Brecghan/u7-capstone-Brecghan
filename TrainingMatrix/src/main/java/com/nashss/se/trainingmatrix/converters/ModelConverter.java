package com.nashss.se.trainingmatrix.converters;

import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.Test;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.models.EmployeeModel;
import com.nashss.se.trainingmatrix.models.TestModel;
import com.nashss.se.trainingmatrix.models.TrainingModel;

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
                .withActive(employee.getIsActive())
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
     * @param Training the Training to convert
     * @return the converted Training
     */
    public TrainingModel toTrainingModel(Training Training) {
        Set<String> employeesTrained = null;
        Set<String> testsForTraining = null;
        if (Training.getEmployeesTrained() != null) {
            employeesTrained = new HashSet<>(Training.getEmployeesTrained());
        }
        if (Training.getTestsForTraining() != null) {
            testsForTraining = new HashSet<>(Training.getTestsForTraining());
        }

        return TrainingModel.builder()
                .withTrainingId(Training.getTrainingId())
                .withTrainingName(Training.getTrainingName())
                .withActive(Training.getIsActive())
                .withMonthsTilExpire(Training.getMonthsTilExpire())
                .withTrainingDate(Training.getTrainingDate())
                .withEmployeesTrained(employeesTrained)
                .withTestsForTraining(testsForTraining)
                .withExpirationStatus(Training.getExpirationStatus())
                .withTrainingSeries(Training.getTrainingSeries())
                .build();
    }

    /**
     * Converts a list of Trainings to a list of TrainingModels.
     *
     * @param Trainings The Trainings to convert to TrainingModels
     * @return The converted list of TrainingModels
     */
    public List<TrainingModel> toTrainingModelList(List<Training> Trainings) {
        List<TrainingModel> TrainingModels = new ArrayList<>();

        for (Training Training : Trainings) {
            TrainingModels.add(toTrainingModel(Training));
        }

        return TrainingModels;
    }

    /**
     * Converts a provided {@link Test} into an {@link TestModel} representation.
     *
     * @param Test the Test to convert
     * @return the converted Test
     */
    public TestModel toTestModel(Test Test) {
        List<String> testAttempts = null;
        if (Test.getTestAttempts() != null) {
            testAttempts = new ArrayList<>(Test.getTestAttempts());
        }

        return TestModel.builder()
                .withTrainingId(Test.getTrainingId())
                .withEmployeeId(Test.getEmployeeId())
                .withHasPassed(Test.getHasPassed())
                .withScoreToPass(Test.getScoreToPass())
                .withLatestScore(Test.getLatestScore())
                .withTestAttempts(testAttempts)
                .build();
    }

    /**
     * Converts a list of Tests to a list of TestModels.
     *
     * @param Tests The Tests to convert to TestModels
     * @return The converted list of TestModels
     */
    public List<TestModel> toTestModelList(List<Test> Tests) {
        List<TestModel> TestModels = new ArrayList<>();

        for (Test Test : Tests) {
            TestModels.add(toTestModel(Test));
        }

        return TestModels;
    }
}
