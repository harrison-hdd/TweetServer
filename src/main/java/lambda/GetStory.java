package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.util.Pair;
import request.authenticated_request.paged_service_request.GetStoryRequest;
import response.paged_service_response.GetStoryResponse;
import service.StatusService;

import java.util.List;

public class GetStory implements RequestHandler<GetStoryRequest, GetStoryResponse> {
    @Override
    public GetStoryResponse handleRequest(GetStoryRequest getStoryRequest, Context context) {
        Pair<List<Status>, Boolean> result = new StatusService().getStory(getStoryRequest);
        return new GetStoryResponse(true, null, result.getFirst(), result.getSecond());
    }
}
