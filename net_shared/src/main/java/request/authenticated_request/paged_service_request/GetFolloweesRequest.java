package request.authenticated_request.paged_service_request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFolloweesRequest extends PagedServiceRequest<User>{
    public GetFolloweesRequest(){
        super();
    }
    public GetFolloweesRequest(AuthToken authToken, User targetUser, User lastItem, int pageSize) {
        super(authToken, targetUser, lastItem, pageSize);
    }
}
