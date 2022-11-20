package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import request.authenticated_request.UnfollowRequest;
import response.UnfollowResponse;
import service.FollowService;

public class Unfollow implements RequestHandler<UnfollowRequest, UnfollowResponse> {

    @Override
    public UnfollowResponse handleRequest(UnfollowRequest unfollowRequest, Context context) {
        return new FollowService().unfollow(unfollowRequest);
    }
}
