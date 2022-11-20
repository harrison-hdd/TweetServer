package response;

public class FollowResponse extends Response{
    public FollowResponse() {
        super();
    }

    public FollowResponse(boolean success) {
        super(success);
    }

    public FollowResponse(boolean success, String message) {
        super(success, message);
    }
}
