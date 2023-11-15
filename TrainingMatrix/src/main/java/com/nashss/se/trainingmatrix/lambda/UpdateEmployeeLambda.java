package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.UpdateEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.UpdateEmployeeResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateEmployeeLambda
        extends LambdaActivityRunner<UpdateEmployeeRequest, UpdateEmployeeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateEmployeeRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateEmployeeRequest> input, Context context) {
        return super.runActivity(

            () -> input.fromBody(UpdateEmployeeRequest.class),
            (request, serviceComponent) ->
                    serviceComponent.provideUpdateEmployeeActivity().handleRequest(request)
        );
    }
}
