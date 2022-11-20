package request;

public class LoginRequest extends AuthenticationRequest{

    public LoginRequest(){
        super();
    }

    public LoginRequest(String username, String password){
        super(username, password);
    }
}
