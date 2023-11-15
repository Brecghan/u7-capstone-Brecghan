package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.TestDao;

import javax.inject.Inject;

public class CreateTestActivity {
    private final TestDao testDao;

    /**
     * Instantiates a new CreateTestActivity object.
     *
     * @param testDao   TestDao to access the Test table.
     */
    @Inject
    public CreateTestActivity(TestDao testDao) {
        this.testDao = testDao;
    }
}
