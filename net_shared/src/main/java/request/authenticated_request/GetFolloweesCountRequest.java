package request.authenticated_request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFolloweesCountRequest extends AuthenticatedRequest{
    private User targetUser;

    public GetFolloweesCountRequest(){
        super();
    }
    public GetFolloweesCountRequest(AuthToken authToken, User targetUser) {
        super(authToken);
        this.targetUser = targetUser;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }
}
