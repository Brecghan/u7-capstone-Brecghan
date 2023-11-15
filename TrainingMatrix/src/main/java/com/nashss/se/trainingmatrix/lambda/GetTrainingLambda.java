package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.GetTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTrainingResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetTrainingLambda extends LambdaActivityRunner<GetTrainingRequest, GetTrainingResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetTrainingRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetTrainingRequest> input, Context context) {
        return super.runActivity(
            () -> {
                return input.fromPath(path ->
                            GetTrainingRequest.builder()
                                    .withTrainingId(path.get("id"))
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideGetTrainingActivity().handleRequest(request)
        );
    }
}
