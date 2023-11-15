package com.nashss.se.trainingmatrix.utils;

public class NameConverter {

    public NameConverter() {
    }

    public String nameConvert(String name) {
        return name.replace(' ', '_');
    }

    public String testNameCreate(String trainingId, String employeeId) {
        return trainingId + "~" + employeeId;
    }
    public String[] testNameSplit(String testName) {
        return testName.split("~");
    }
}
