package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.CreateTrainingRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateTrainingResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateTrainingLambda
        extends LambdaActivityRunner<CreateTrainingRequest, CreateTrainingResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateTrainingRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateTrainingRequest> input, Context context) {
        return super.runActivity(

            () -> input.fromBody(CreateTrainingRequest.class),
            (request, serviceComponent) ->
                    serviceComponent.provideCreateTrainingActivity().handleRequest(request)
        );
    }
}
