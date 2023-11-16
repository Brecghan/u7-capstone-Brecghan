package com.nashss.se.trainingmatrix.activity.requests;

public class GetTestRequest {
    private final String testId;

    private GetTestRequest(String testId) {
        this.testId = testId;
    }

    public String getTestId() {
        return testId;
    }

    @Override
    public String toString() {
        return "GetTestRequest{" +
                "testId='" + testId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String testId;

        public Builder withTestId(String testId) {
            this.testId = testId;
            return this;
        }

        public GetTestRequest build() {
            return new GetTestRequest(testId);
        }
    }
}
