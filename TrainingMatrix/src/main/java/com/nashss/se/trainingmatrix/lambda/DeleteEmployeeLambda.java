package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.DeleteEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.DeleteEmployeeResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DeleteEmployeeLambda
        extends LambdaActivityRunner<DeleteEmployeeRequest, DeleteEmployeeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteEmployeeRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteEmployeeRequest> input, Context context) {
        return super.runActivity(
            () -> {
                return input.fromPath(path ->
                        DeleteEmployeeRequest.builder()
                                .withEmployeeId(path.get("id"))
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideDeleteEmployeeActivity().handleRequest(request)
        );
    }
}
