package request.authenticated_request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetUserRequest extends AuthenticatedRequest{
    private String username;

    public GetUserRequest(){
        super();
    }

    public GetUserRequest(AuthToken authToken, String username){
        super(authToken);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
