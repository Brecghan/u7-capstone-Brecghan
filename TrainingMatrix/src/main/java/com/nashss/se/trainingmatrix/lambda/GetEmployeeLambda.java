package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.GetEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.GetEmployeeResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetEmployeeLambda  extends LambdaActivityRunner<GetEmployeeRequest, GetEmployeeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetEmployeeRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetEmployeeRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    return input.fromPath(path ->
                            GetEmployeeRequest.builder()
                                    .withEmployeeId(path.get("id"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetEmployeeActivity().handleRequest(request)
        );
    }
}
