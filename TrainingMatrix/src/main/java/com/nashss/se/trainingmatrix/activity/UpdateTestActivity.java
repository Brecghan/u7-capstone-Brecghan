package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.TestDao;

import javax.inject.Inject;

public class UpdateTestActivity {
    private final TestDao testDao;

    /**
     * Instantiates a new UpdateTestActivity object.
     *
     * @param testDao   TestDao to access the Test table.
     */
    @Inject
    public UpdateTestActivity(TestDao testDao) {
        this.testDao = testDao;
    }
}
