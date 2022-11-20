package request.authenticated_request.paged_service_request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetStoryRequest extends PagedServiceRequest<Status> {
    public GetStoryRequest() {
        super();
    }

    public GetStoryRequest(AuthToken authToken, User targetUser, Status lastItem, int pageSize) {
        super(authToken, targetUser, lastItem, pageSize);
    }
}
