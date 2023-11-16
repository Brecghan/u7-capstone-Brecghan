package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.GetTestRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTestResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetTestLambda extends LambdaActivityRunner<GetTestRequest, GetTestResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetTestRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetTestRequest> input, Context context) {
        return super.runActivity(
            () -> {
                return input.fromPath(path ->
                            GetTestRequest.builder()
                                    .withTestId(path.get("id"))
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideGetTestActivity().handleRequest(request)
        );
    }
}
