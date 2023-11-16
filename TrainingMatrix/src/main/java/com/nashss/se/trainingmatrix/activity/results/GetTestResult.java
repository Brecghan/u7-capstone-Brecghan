package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.TestModel;

public class GetTestResult {
    private final TestModel test;

    private GetTestResult(TestModel test) {
        this.test = test;
    }

    public TestModel getTest() {
        return test;
    }

    @Override
    public String toString() {
        return "GetTestResult{" +
                "test=" + test +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private TestModel test ;

        public Builder withTest(TestModel test) {
            this.test = test;
            return this;
        }

        public GetTestResult build() {
            return new GetTestResult(test);
        }
    }
}
