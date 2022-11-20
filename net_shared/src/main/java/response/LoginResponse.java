package response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginResponse extends Response{
    private User user;
    private AuthToken authToken;

    public LoginResponse(){
        super();
    }


    public LoginResponse(boolean success, User user, AuthToken authToken){
        super(success);
        this.user = user;
        this.authToken = authToken;
    }

    public LoginResponse(boolean success, String message, User user, AuthToken authToken){
        super(success, message);
        this.user = user;
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public LoginResponse(boolean success, String message){
        super(success, message);
    }
}
