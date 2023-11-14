package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.GetEmployeeListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetEmployeeListResult;
import com.nashss.se.trainingmatrix.dynamodb.models.enums.Team;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetEmployeeListLambda extends LambdaActivityRunner<GetEmployeeListRequest, GetEmployeeListResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetEmployeeListRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetEmployeeListRequest> input, Context context) {
        return super.runActivity(
            () -> {
                return input.fromQuery(query ->
                            GetEmployeeListRequest.builder()
                                    .withIsActive(Boolean.valueOf(query.get("isActive")))
                                    .withTeam(query.get("team"))
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideGetEmployeeListActivity().handleRequest(request)
        );
    }
}
