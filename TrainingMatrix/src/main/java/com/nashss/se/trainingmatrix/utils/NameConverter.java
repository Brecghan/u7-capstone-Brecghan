package com.nashss.se.trainingmatrix.utils;

public class NameConverter {

    /**
     * Converts Names into the proper form for storage.
      */
    public NameConverter() {
    }

    /**
     * Converts a provided string by replacing spaces with underscores.
     *
     * @param name the name to convert
     * @return the converted name
     */
    public String nameConvert(String name) {
        return name.replace(' ', '_');
    }

    /**
     * Combines the trainingID and the EmployeeID into a single string
     * for storage in Trainings and Employees.
     *
     * @param trainingId the test's trainingID
     * @param employeeId the test's employeeID
     * @return the combined testName
     */
    public String testNameCreate(String trainingId, String employeeId) {
        return trainingId + "~" + employeeId;
    }

    /**
     * Splits the trainingID and the EmployeeID from a single string
     * to allow retrieval from the database.
     *
     * @param testName the testName to be split
     * @return a String Array with the trainingID and the EmployeeID
     */
    public String[] testNameSplit(String testName) {
        return testName.split("~");
    }
}
