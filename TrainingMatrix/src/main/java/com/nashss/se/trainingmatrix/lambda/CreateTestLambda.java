package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.CreateTestRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateTestResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateTestLambda
        extends LambdaActivityRunner<CreateTestRequest, CreateTestResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateTestRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateTestRequest> input, Context context) {
        return super.runActivity(
            
            () -> input.fromBody(CreateTestRequest.class),
            (request, serviceComponent) ->
                    serviceComponent.provideCreateTestActivity().handleRequest(request)
        );
    }
}
