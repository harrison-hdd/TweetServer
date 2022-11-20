package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import request.authenticated_request.FollowRequest;
import response.FollowResponse;
import service.FollowService;

public class Follow implements RequestHandler<FollowRequest, FollowResponse> {

    @Override
    public FollowResponse handleRequest(FollowRequest followRequest, Context context) {
        return new FollowService().follow(followRequest);
    }
}
