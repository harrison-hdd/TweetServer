package request.authenticated_request.paged_service_request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersRequest extends PagedServiceRequest<User>{
    public GetFollowersRequest(){
        super();
    }

    public GetFollowersRequest(AuthToken authToken, User targetUser, User lastItem, int pageSize) {
        super(authToken, targetUser, lastItem, pageSize);
    }
}
