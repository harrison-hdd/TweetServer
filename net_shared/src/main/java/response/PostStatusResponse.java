package response;

public class PostStatusResponse extends Response{
    public PostStatusResponse() {
        super();
    }

    public PostStatusResponse(boolean success) {
        super(success);
    }

    public PostStatusResponse(boolean success, String message) {
        super(success, message);
    }
}
