# Design Document

## Instructions

## Brecghan's Training Matrix(BTM) Design

## 1. Problem Statement

This service is designed to manage a training system for a company's personnel. It allows them to track active employees, what training they have received, what testing they have taken and the results of the test, and if any trainings are set to expire.

This design document describes the service that will provide the training matrix functionality to meet our customers' needs. It is designed to contain employees, trainings, and a testing tracker. It will be able to return current employees, trainings that have been conducted, and tests results from the different trainings.

## 2. Use Cases

U1. As a BTM customer, I want to create an employee

U2. As a BTM customer, I want to be able to create a training

U3. As a BTM customer, I want to be able to create a training series

U4. As a BTM customer, I want to be able to create tests for trainings

U5. As a BTM customer, I want to be able to view all active trainings that are close to expiration

U6. As a BTM customer, I want to be able to update test results for retests

U7. As a BTM customer, I want to be able to view all trainings done within a series

U8. As a BTM customer, I want to be able to view specific trainings

U9. As a BTM customer, I want to be able to view all employees on a specific team

U10. As a BTM customer, I want to be able to view all tests taken by an employee

U11. As a BTM customer, I want to be able to deactivate employees that have left


Stretch goals

S1. As a BTM customer, I want to have my trainings auto-update their status based off of their date and months to expiration

S2. As a BTM customer, I want to be able to make the next training in a training series by copying the information from the most recent training


## 3. Project Scope

### 3.1. In Scope

* Creating, retrieving, and updating an employee
* Test list that can be searched by training or employee name
* Recipes can be retrieved and individual recipes added to meal plan
* A user's meal plan which consists of chosen recipes

### 3.2. Out of Scope

* Trainings auto-updating their status
* Training Series duplicator for next in series

# 4. Proposed Architecture Overview

This initial iteration will provide the minimum lovable product (MLP) including
creating, retrieving, and updating an employee, as well as retrieving a training and creating testing for it; and creating a Training Series.

We will use API Gateway and Lambda to create 15(20) endpoints (defined in section 6.2 "Endpoints")
that will handle the creation, update, and retrieval of Employees, Trainings, Tests and Training Series to satisfy our
requirements.

We will store employees, trainings, tests, and training series in their own tables in DynamoDB.

Brecghan's Training Matrix(BTM) will also provide a web interface for users to manage
the matrix. A main page providing connections to view trainings, employees, or test results. From those pages Users
will be able to create new items and link off to the other viewing pages.

# 5. API

## 6.1. Public Models

```
// EmployeeModel

String employeeId;
String employeeName;
Boolean isActive;
Enum team;
Date startDate;
Set<String> trainingsTaken;
Set<String> testsTaken;
Enum trainingStatus;
```

```
// TrainingModel

String trainingId;
String trainingName;
ZoneDateTime trainingDate;
Boolean isActive;
Integer monthsTilExpire;
Enum expirationStatus;
Set<String> testsForTraining;
Set<String> employeesTrained;
String trainingSeries;

```

```
// TestModel

String trainingId;
String employeeId;
Integer scoreToPass;
Integer latestScore;
Boolean hasPassed;
Integer timesTaken;
```

```
// TrainingSeriesModel

String trainingSeriesName;
```

## 6.2. Endpoints

### Employees

* Accepts `POST` requests to `/employee`
* Accepts an employeeId, employeeName, start Date, and team; returns the corresponding EmployeeModel including a true boolean parameter isActive, a training status of Up-To-Date, and 2 blank sets of trainingsTaken and testsTaken.

* Accepts `GET` requests to `/employee`
* Returns a list of all employees.

* Accepts `GET` requests to `/employee?isActive={isActive}`
* Returns a list of employees. If specified through the boolean query parameter, returns only Active employees.

* Accepts `GET` requests to `/employee?team={team}`
* Accepts a team and returns the employees within the corresponding team. 
 
* Accepts `GET` requests to `/employee/{employeeId}`
* Accepts an employeeId and returns the corresponding EmployeeModel.

* Accepts `PUT` requests to `/employee/{employeeId}`
* Accepts data to update an employee including an updated employeeName, team, and isActive boolean.

* Accepts `GET` requests to `/employee/{employeeId}/tests`
* Accepts an employeeId returns the corresponding list of tests for that employee.

### Training Series

* Accepts `POST` requests to `/trainingSeries`
* Accepts a trainingSeriesName and returns a list of Training Series, containing the newly added Series.

* Accepts `GET` requests to `/trainingSeries`
* Returns a list of Training Series.

* Accepts `GET` requests to `/trainingSeries/trainings`
* Returns a list of all trainings for a specific Training Series.

### Trainings

* Accepts `POST` requests to `/training`
* Accepts a trainingName, trainingDate, trainingSeries(can be null), and monthsTilExpire and returns the corresponding TrainingModel including a unique trainingId assigned by the Training Service.

* Accepts `GET` requests to `/training`
* Returns a list of all Trainings.

* Accepts `GET` requests to `/training?isActive={isActive}`
* Returns a list of all Trainings. If specified through the boolean query parameter, returns only Active trainings.

* Accepts `GET` requests to `/training?isActive={isActive}&expirationStatus={expirationStatus}`
* Returns a list of all Trainings. If specified through the query parameters, returns only Active trainings close to expiring.

* Accepts `PUT` requests to `/training/{trainingId}`
* Accepts data to update a training all instance variables other than name, date, trainingSeries, and ID can be updated.

* Accepts `GET` requests to `/training/{trainingId}`
* Accepts a trainingId returns the corresponding training.

* Accepts `POST` requests to `/training/{trainingId}/tests`
* Accepts a list of employeeIDs and an integer scoreToPass, it then creates a test for this training for each employee on the list.

* Accepts `GET` requests to `/training/{trainingId}/tests`
* Returns a list of all tests for the specified training.

* Accepts `GET` requests to `/training/{trainingId}/tests?hasPassed={hasPassed}`
* Returns a list of all tests for the specified training. If specified through the query parameters, returns only tests passed/failed..

### Tests

* Accepts `PUT` requests to `/test`
* Accepts a trainingId, an employeeId, and a score update and returns the corresponding TestModel.

# 7. Tables

### 7.1. `employees`

```
employeeId // partition key, string
employeeName // string
isActive // boolean
team // string employees-by-team-index partition key
startDate // string
trainingsTaken // string set 
testsTaken // string set
trainingStatus // string
```

### 7.2. `trainings`

```
trainingId // partition key, string
trainingName // string
trainingDate // string
isActive // boolean
monthsTilExpire // number
expirationStatus // string
testsForTraining // string set
employeesTrained // string set
trainingSeries // string trainings-by-series-index partition key
```
 
### 7.3. `tests`

```
trainingId // partition key, string
employeeId // sort key, string tests-by-employee-index partition key
scoreToPass // number
latestScore // number
hasPassed // boolean
timesTaken // number
```

### 7.4. `trainingSeries`

```
trainingSeriesName // partition key, string
```

- `tests-by-employee-index` includes ALL attributes
- `trainings-by-series-index` includes ALL attributes
- `employees-by-team-index` includes ALL attributes


# 8. Pages
