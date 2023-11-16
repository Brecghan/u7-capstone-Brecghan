package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.UpdateTestRequest;
import com.nashss.se.trainingmatrix.activity.results.UpdateTestResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateTestLambda
        extends LambdaActivityRunner<UpdateTestRequest, UpdateTestResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateTestRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateTestRequest> input, Context context) {
        return super.runActivity(

            () -> input.fromBody(UpdateTestRequest.class),
            (request, serviceComponent) ->
                    serviceComponent.provideUpdateTestActivity().handleRequest(request)
        );
    }
}
