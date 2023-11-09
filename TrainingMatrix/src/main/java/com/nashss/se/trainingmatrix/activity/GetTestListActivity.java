package com.nashss.se.trainingmatrix.activity;

import com.nashss.se.trainingmatrix.dynamodb.TestDao;

import javax.inject.Inject;

public class GetTestListActivity {
    private final TestDao testDao;

    /**
     * Instantiates a new GetTestListActivity object.
     *
     * @param testDao   TestDao to access the Test table.
     */
    @Inject
    public GetTestListActivity(TestDao testDao) {
        this.testDao = testDao;
    }
}
