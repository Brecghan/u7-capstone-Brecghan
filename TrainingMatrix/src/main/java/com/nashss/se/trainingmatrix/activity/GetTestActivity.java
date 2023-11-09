package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.TestDao;

import javax.inject.Inject;

public class GetTestActivity {
    private final TestDao testDao;

    /**
     * Instantiates a new GetTestActivity object.
     *
     * @param testDao   TestDao to access the Test table.
     */
    @Inject
    public GetTestActivity(TestDao testDao) {
        this.testDao = testDao;
    }
}
