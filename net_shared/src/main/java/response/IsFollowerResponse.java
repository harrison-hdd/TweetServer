package response;

public class IsFollowerResponse extends Response{
    private boolean isFollower;

    public IsFollowerResponse(){
        super();
    }
    public IsFollowerResponse(boolean success, String message, boolean isFollower) {
        super(success, message);
        this.isFollower = isFollower;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setFollower(boolean follower) {
        isFollower = follower;
    }
}
