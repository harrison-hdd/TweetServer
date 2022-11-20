package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.util.Pair;
import request.authenticated_request.paged_service_request.GetFeedRequest;
import response.paged_service_response.GetFeedResponse;
import service.StatusService;

import java.util.List;

public class GetFeed implements RequestHandler<GetFeedRequest, GetFeedResponse> {

    @Override
    public GetFeedResponse handleRequest(GetFeedRequest getFeedRequest, Context context) {
        Pair<List<Status>, Boolean> result = new StatusService().getFeed(getFeedRequest);
        return new GetFeedResponse(true, null, result.getFirst(), result.getSecond());
    }
}
