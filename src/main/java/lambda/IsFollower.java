package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import request.authenticated_request.IsFollowerRequest;
import response.IsFollowerResponse;
import service.FollowService;

public class IsFollower implements RequestHandler<IsFollowerRequest, IsFollowerResponse> {

    @Override
    public IsFollowerResponse handleRequest(IsFollowerRequest isFollowerRequest, Context context) {
        return new FollowService().isFollower(isFollowerRequest);
    }
}
