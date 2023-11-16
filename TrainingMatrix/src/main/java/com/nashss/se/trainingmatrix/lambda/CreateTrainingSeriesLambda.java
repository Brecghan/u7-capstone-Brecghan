package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.CreateTrainingSeriesRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateTrainingSeriesResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateTrainingSeriesLambda
        extends LambdaActivityRunner<CreateTrainingSeriesRequest, CreateTrainingSeriesResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateTrainingSeriesRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateTrainingSeriesRequest> input,
                                        Context context) {
        return super.runActivity(

            () -> input.fromBody(CreateTrainingSeriesRequest.class),
            (request, serviceComponent) ->
                    serviceComponent.provideCreateTrainingSeriesActivity().handleRequest(request)
        );
    }
}
