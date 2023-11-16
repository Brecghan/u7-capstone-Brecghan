package com.nashss.se.trainingmatrix.activity.results;

import com.nashss.se.trainingmatrix.models.TestModel;

import java.util.List;

public class GetTestListResult {
    private final List<TestModel> tests;

    private GetTestListResult(List<TestModel> tests) {
        this.tests = tests;
    }

    public List<TestModel> getTests() {
        return tests;
    }

    @Override
    public String toString() {
        return "GetTestListResult{" +
                "tests=" + tests +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<TestModel> tests ;

        public Builder withTests(List<TestModel> tests) {
            this.tests = tests;
            return this;
        }

        public GetTestListResult build() {
            return new GetTestListResult(tests);
        }
    }
}
