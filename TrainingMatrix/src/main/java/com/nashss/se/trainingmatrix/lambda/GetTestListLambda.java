package com.nashss.se.trainingmatrix.lambda;

import com.nashss.se.trainingmatrix.activity.requests.GetTestListRequest;
import com.nashss.se.trainingmatrix.activity.results.GetTestListResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetTestListLambda extends LambdaActivityRunner<GetTestListRequest, GetTestListResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetTestListRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetTestListRequest> input, Context context) {
        return super.runActivity(
            () -> {
                return input.fromQuery(query ->
                            GetTestListRequest.builder()
                                    .withHasPassed(query.get("hasPassed"))
                                    .withTrainingId(query.get("trainingId"))
                                    .withEmployeeId(query.get("employeeId"))
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideGetTestListActivity().handleRequest(request)
        );
    }
}
