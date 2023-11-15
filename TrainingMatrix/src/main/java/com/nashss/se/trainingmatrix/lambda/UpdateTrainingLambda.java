package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.UpdateTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.UpdateTrainingResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateTrainingLambda
        extends LambdaActivityRunner<UpdateTrainingRequest, UpdateTrainingResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateTrainingRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateTrainingRequest> input, Context context) {
        return super.runActivity(

            () -> input.fromBody(UpdateTrainingRequest.class),
            (request, serviceComponent) ->
                    serviceComponent.provideUpdateTrainingActivity().handleRequest(request)
        );
    }
}
