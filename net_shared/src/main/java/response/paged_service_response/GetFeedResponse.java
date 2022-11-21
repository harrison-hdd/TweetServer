package response.paged_service_response;

import edu.byu.cs.tweeter.model.domain.Status;

import java.util.List;

public class GetFeedResponse extends PagedServiceResponse<Status> {

    public GetFeedResponse() {
    }

    public GetFeedResponse(boolean success, String message, List<Status> items, boolean hasMorePages) {
        super(success, message, items, hasMorePages);
    }
}
