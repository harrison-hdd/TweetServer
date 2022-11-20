package response;

public class GetFollowersCountResponse extends Response{
    private int followersCount;

    public GetFollowersCountResponse(){
        super();
    }

    public GetFollowersCountResponse(boolean success){
        super(success);
    }

    public GetFollowersCountResponse(boolean success, String message, Integer followersCount) {
        super(success, message);
        this.followersCount = followersCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }
}
