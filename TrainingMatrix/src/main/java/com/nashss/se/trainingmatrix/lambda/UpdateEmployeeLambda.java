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
            //            () -> {
            //                UpdateEmployeeRequest unauthenticatedRequest =
            //                input.fromBody(UpdateEmployeeRequest.class);
            //                System.out.println("Papa can you hear me?");
            //                return input.fromUserClaims(claims ->
            //                        UpdateEmployeeRequest.builder()
            //                                .withEmployeeName(unauthenticatedRequest.getEmployeeName())
            //                                .withEmployeeId(unauthenticatedRequest.getEmployeeId())
            //                                .withIsActive(unauthenticatedRequest.getIsActive())
            //                                .withTeam(unauthenticatedRequest.getTeam())
            //                                .withStartDate(unauthenticatedRequest.getStartDate())
            //                                .build()); }


            () -> input.fromBody(UpdateEmployeeRequest.class),
            (request, serviceComponent) ->
                    serviceComponent.provideUpdateEmployeeActivity().handleRequest(request)
        );
    }
}
