package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.GetTrainingSeriesListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTrainingSeriesListResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetTrainingSeriesListLambda extends LambdaActivityRunner<GetTrainingSeriesListRequest,
        GetTrainingSeriesListResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetTrainingSeriesListRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetTrainingSeriesListRequest> input,
                                        Context context) {
        return super.runActivity(
            () -> {
                return input.fromQuery(query ->
                            GetTrainingSeriesListRequest.builder()
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideGetTrainingSeriesListActivity().handleRequest(request)
        );
    }
}
