package response;

public class GetFolloweesCountResponse extends Response{
    private int followeesCount;

    public GetFolloweesCountResponse(){
        super();
    }
    public GetFolloweesCountResponse(boolean success, String message, int followeesCount) {
        super(success, message);
        this.followeesCount = followeesCount;
    }

    public int getFolloweesCount() {
        return followeesCount;
    }

    public void setFolloweesCount(int followeesCount) {
        this.followeesCount = followeesCount;
    }
}
