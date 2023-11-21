package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.DeleteTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.DeleteTrainingResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DeleteTrainingLambda
        extends LambdaActivityRunner<DeleteTrainingRequest, DeleteTrainingResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteTrainingRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteTrainingRequest> input, Context context) {
        return super.runActivity(
            () -> {
                return input.fromPath(path ->
                        DeleteTrainingRequest.builder()
                                .withTrainingId(path.get("id"))
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideDeleteTrainingActivity().handleRequest(request)
        );
    }
}
