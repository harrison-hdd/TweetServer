package request.authenticated_request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusRequest extends AuthenticatedRequest{
    Status status;

    public PostStatusRequest() {
        super();
    }

    public PostStatusRequest(AuthToken authToken, Status status) {
        super(authToken);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
