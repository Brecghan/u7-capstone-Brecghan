package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.activity.requests.UpdateTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.UpdateTrainingResult;
import com.nashss.se.trainingmatrix.converters.ModelConverter;
import com.nashss.se.trainingmatrix.dynamodb.EmployeeDao;
import com.nashss.se.trainingmatrix.dynamodb.TrainingDao;
import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.Training;
import com.nashss.se.trainingmatrix.models.TrainingModel;

import java.util.Set;
import javax.inject.Inject;

public class UpdateTrainingActivity {
    private final TrainingDao trainingDao;
    private final EmployeeDao employeeDao;

    /**
     * Instantiates a new UpdateTrainingActivity object.
     *
     * @param trainingDao   TrainingDao to access the Training table.
     * @param employeeDao   EmployeeDao to access the Employee table.
     */
    @Inject
    public UpdateTrainingActivity(TrainingDao trainingDao, EmployeeDao employeeDao) {
        this.trainingDao = trainingDao;
        this.employeeDao = employeeDao;
    }

    /**
     * This method handles the incoming request by retrieving a training using the
     * provided training ID and updating the isActive, monthsTilExpire, expirationStatus,
     * testsForTraining, and employeesTrained.
     * <p>
     * It then returns the newly updated training.
     * <p>
     *
     * @param updateTrainingRequest request object containing the training ID, sActive, monthsTilExpire,
     *                              expirationStatus, testsForTraining, and employeesTrained associated with it
     * @return updateTrainingResult result object containing the API defined {@link TrainingModel}
     */
    public UpdateTrainingResult handleRequest(final UpdateTrainingRequest updateTrainingRequest) {
        Training training = trainingDao.getTraining(updateTrainingRequest.getTrainingId());

        if (updateTrainingRequest.getMonthsTilExpire() != null) {
            training.setMonthsTilExpire(updateTrainingRequest.getMonthsTilExpire());
        }
        if (updateTrainingRequest.getIsActive() != null) {
            training.setIsActive(updateTrainingRequest.getIsActive());
        }
        if (updateTrainingRequest.getExpirationStatus() != null) {
            training.setExpirationStatus(updateTrainingRequest.getExpirationStatus());
        }

        if (!updateTrainingRequest.getTestsForTraining().isEmpty()) {
            Set<String> updateTests = training.getTestsForTraining();
            updateTests.addAll(updateTrainingRequest.getTestsForTraining());
            training.setTestsForTraining(updateTests);
        }

        if (!updateTrainingRequest.getEmployeesTrained().isEmpty()) {
            Set<String> updateEmployees = training.getEmployeesTrained();
            updateEmployees.addAll(updateTrainingRequest.getEmployeesTrained());
            training.setEmployeesTrained(updateEmployees);
            addTrainingToEmployee(updateEmployees, updateTrainingRequest.getTrainingId());
        }

        trainingDao.saveTraining(training);

        TrainingModel trainingModel = new ModelConverter().toTrainingModel(training);
        return UpdateTrainingResult.builder()
                .withTraining(trainingModel)
                .build();
    }

    /**
     * This method updates all the Employees that have been added to the training.
     * <p>
     *
     * @param employees The set of employees added to the training
     * @param trainingId the ID of the training
     */
    private void addTrainingToEmployee(Set<String> employees, String trainingId) {
        for (String employeeId : employees) {
            Employee employee = employeeDao.getEmployee(employeeId);
            System.out.println("***********************************LOOK HERE***********************");
            Set<String> trainingsTaken = employee.getTrainingsTaken();
            trainingsTaken.add(trainingId);
            employee.setTrainingsTaken(trainingsTaken);
            employeeDao.saveEmployee(employee);
        }
    }
}
