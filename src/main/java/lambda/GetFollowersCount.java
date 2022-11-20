package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import request.authenticated_request.GetFollowersCountRequest;
import response.GetFollowersCountResponse;
import service.FollowService;

public class GetFollowersCount implements RequestHandler<GetFollowersCountRequest, GetFollowersCountResponse> {
    @Override
    public GetFollowersCountResponse handleRequest(GetFollowersCountRequest getFollowersCountRequest, Context context) {
        return new FollowService().getFollowersCount(getFollowersCountRequest);
    }
}
