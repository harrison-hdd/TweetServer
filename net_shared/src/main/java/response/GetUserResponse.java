package response;

import edu.byu.cs.tweeter.model.domain.User;

public class GetUserResponse extends Response{


    private User user;

    public GetUserResponse(){
        super();
    }


    public GetUserResponse(boolean success, User user){
        this(success, null, user);
    }

    public GetUserResponse(boolean success, String message, User user){
        super(success, message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
