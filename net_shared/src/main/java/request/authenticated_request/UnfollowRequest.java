package request.authenticated_request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UnfollowRequest extends AuthenticatedRequest{
    private User follower;
    private User followee;

    public UnfollowRequest() {
        super();
    }

    public UnfollowRequest(AuthToken authToken, User follower, User followee) {
        super(authToken);
        this.follower = follower;
        this.followee = followee;
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}
