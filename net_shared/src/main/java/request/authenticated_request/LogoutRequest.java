package request.authenticated_request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class LogoutRequest extends AuthenticatedRequest {

    public LogoutRequest(){
        super();
    }

    public LogoutRequest(AuthToken authToken){
        super(authToken);
    }

}
