package request.authenticated_request.paged_service_request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFeedRequest extends PagedServiceRequest<Status>{
    public GetFeedRequest() {
        super();
    }

    public GetFeedRequest(AuthToken authToken, User targetUser, Status lastItem, int pageSize) {
        super(authToken, targetUser, lastItem, pageSize);
    }
}
