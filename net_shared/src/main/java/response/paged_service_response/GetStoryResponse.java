package response.paged_service_response;

import edu.byu.cs.tweeter.model.domain.Status;

import java.util.List;

public class GetStoryResponse extends PagedServiceResponse<Status>{
    public GetStoryResponse() {
    }

    public GetStoryResponse(boolean success, String message, List<Status> items, boolean hasMorePages) {
        super(success, message, items, hasMorePages);
    }
}
