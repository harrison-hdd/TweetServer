package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;
import request.authenticated_request.paged_service_request.GetFolloweesRequest;
import response.paged_service_response.GetFolloweesResponse;
import service.FollowService;

import java.util.List;

public class GetFollowees implements RequestHandler<GetFolloweesRequest, GetFolloweesResponse> {
    @Override
    public GetFolloweesResponse handleRequest(GetFolloweesRequest getFolloweesRequest, Context context) {
        Pair<List<User>, Boolean> result = new FollowService().getFollowees(getFolloweesRequest);
        return new GetFolloweesResponse(true, null, result.getFirst(), result.getSecond());
    }
}
