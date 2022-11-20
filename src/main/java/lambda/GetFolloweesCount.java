package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import request.authenticated_request.GetFolloweesCountRequest;
import response.GetFolloweesCountResponse;
import service.FollowService;

public class GetFolloweesCount implements RequestHandler<GetFolloweesCountRequest, GetFolloweesCountResponse> {
    @Override
    public GetFolloweesCountResponse handleRequest(GetFolloweesCountRequest getFolloweesCountRequest, Context context) {
        return new FollowService().getFolloweesCount(getFolloweesCountRequest);
    }
}
