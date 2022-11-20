package response;

public class UnfollowResponse extends Response{
    public UnfollowResponse() {
        super();
    }

    public UnfollowResponse(boolean success) {
        super(success);
    }

    public UnfollowResponse(boolean success, String message) {
        super(success, message);
    }
}
