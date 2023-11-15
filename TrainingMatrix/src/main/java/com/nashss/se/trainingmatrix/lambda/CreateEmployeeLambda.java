package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.CreateEmployeeRequest;
import com.nashss.se.trainingmatrix.activity.results.CreateEmployeeResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateEmployeeLambda
        extends LambdaActivityRunner<CreateEmployeeRequest, CreateEmployeeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateEmployeeRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateEmployeeRequest> input, Context context) {
        return super.runActivity(
            //            () -> {
            //                CreateEmployeeRequest unauthenticatedRequest =
            //                input.fromBody(CreateEmployeeRequest.class);
            //                System.out.println("Papa can you hear me?");
            //                return input.fromUserClaims(claims ->
            //                        CreateEmployeeRequest.builder()
            //                                .withEmployeeName(unauthenticatedRequest.getEmployeeName())
            //                                .withEmployeeId(unauthenticatedRequest.getEmployeeId())
            //                                .withIsActive(unauthenticatedRequest.getIsActive())
            //                                .withTeam(unauthenticatedRequest.getTeam())
            //                                .withStartDate(unauthenticatedRequest.getStartDate())
            //                                .build()); }


            () -> input.fromBody(CreateEmployeeRequest.class),
            (request, serviceComponent) ->
                    serviceComponent.provideCreateEmployeeActivity().handleRequest(request)
        );
    }
}
