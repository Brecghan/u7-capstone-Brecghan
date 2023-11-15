package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.GetTrainingListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTrainingListResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetTrainingListLambda extends LambdaActivityRunner<GetTrainingListRequest, GetTrainingListResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetTrainingListRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetTrainingListRequest> input, Context context) {
        return super.runActivity(
            () -> {
                return input.fromQuery(query ->
                            GetTrainingListRequest.builder()
                                    .withIsActive(Boolean.valueOf(query.get("isActive")))
                                    .withStatus(query.get("status"))
                                    .withTrainingSeries(query.get("trainingSeries"))
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideGetTrainingListActivity().handleRequest(request)
        );
    }
}
