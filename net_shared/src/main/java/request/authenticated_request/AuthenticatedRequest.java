package request.authenticated_request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

import java.io.Serializable;

public abstract class AuthenticatedRequest implements Serializable {
    protected AuthToken authToken;

    protected AuthenticatedRequest(){}

    protected AuthenticatedRequest(AuthToken authToken){
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
