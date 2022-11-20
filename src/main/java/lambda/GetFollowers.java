package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;
import request.authenticated_request.paged_service_request.GetFollowersRequest;
import response.paged_service_response.GetFollowersResponse;
import service.FollowService;

import java.util.List;

public class GetFollowers implements RequestHandler<GetFollowersRequest, GetFollowersResponse>{

    @Override
    public GetFollowersResponse handleRequest(GetFollowersRequest getFollowersRequest, Context context) {
        Pair<List<User>, Boolean> result = new FollowService().getFollowers(getFollowersRequest);
        return new GetFollowersResponse(result.getFirst(), result.getSecond());
    }
}