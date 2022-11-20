package response.paged_service_response;

import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class GetFollowersResponse extends PagedServiceResponse<User> {
    public GetFollowersResponse() {
        super();
    }

    public GetFollowersResponse(List<User> items, boolean hasMorePages) {
        super(items, hasMorePages);
    }
}
