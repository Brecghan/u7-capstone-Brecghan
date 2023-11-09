package com.nashss.se.trainingmatrix.dynamodb;

import com.nashss.se.trainingmatrix.dynamodb.models.Employee;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;
import com.nashss.se.trainingmatrix.exceptions.EmployeeNotFoundException;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for an Employee using {@link Employee} to represent the model in DynamoDB.
 */
@Singleton
public class EmployeeDao {
    private final DynamoDBMapper dynamoDbMapper;
    
    /**
     * Instantiates a EmployeeDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the employees table
     */
    @Inject
    public EmployeeDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the {@link Employee} corresponding to the specified id.
     *
     * @param employeeId the employee ID
     * @return the stored Employee, or null if none was found.
     */
    public Employee getEmployee(String employeeId) {
        Employee employee = this.dynamoDbMapper.load(Employee.class, employeeId);

        if (employee == null) {
            throw new EmployeeNotFoundException("Could not find employee with id " + employeeId);
        }
        return employee;
    }

    /**
     * Saves (creates or updates) the given Employee.
     *
     * @param employee The Employee to save
     * @return The Employee object that was saved
     */
    public Employee saveEmployee(Employee employee) {
        this.dynamoDbMapper.save(employee);
        return employee;
    }
    /**
     * Perform a search (via a "scan") of the Employee table for employees matching the given criteria.
     * <p>
     * The criteria options are isActive, in which only employees that match the status passed will
     * be returned, and Team, in which only employees that match the team passed will be returned.
     * If no criteria is specified, all employees will be returned
     *
     * @param isActive a Boolean containing the employee status requested or null.
     * @param team an Enum containing an employee team requested or null.
     * @return a List of Employee objects that match the search criteria.
     */
    public List<Employee> getEmployeeList(Boolean isActive, Team team) {
        if (!isActive && team == null) {
            DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();

            return this.dynamoDbMapper.scan(Employee.class, dynamoDBScanExpression);

        } else if (isActive && team == null) {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":isActive", new AttributeValue().withBOOL(isActive));
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression("isActive = :isActive")
                    .withExpressionAttributeValues(valueMap);

            return dynamoDbMapper.scan(Employee.class, scanExpression);

        } else {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":team", new AttributeValue().withS(String.valueOf(team)));
            valueMap.put(":isActive", new AttributeValue().withBOOL(isActive));
            DynamoDBQueryExpression<Employee> queryExpression = new DynamoDBQueryExpression<Employee>()
                    .withIndexName(Employee.EMPLOYEES_BY_TEAM_INDEX)
                    .withConsistentRead(false)
                    .withKeyConditionExpression("isActive = :isActive and team = :team")
                    .withExpressionAttributeValues(valueMap);

            return dynamoDbMapper.query(Employee.class, queryExpression);
        }
    }
}
