package com.nashss.se.trainingmatrix.activity.requests;

public class GetTrainingSeriesListRequest {

    private GetTrainingSeriesListRequest() {
    }

    @Override
    public String toString() {
        return "GetTrainingSeriesListRequest{" +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        public GetTrainingSeriesListRequest build() {
            return new GetTrainingSeriesListRequest();
        }
    }
}
