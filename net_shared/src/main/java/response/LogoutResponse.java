package response;

public class LogoutResponse extends Response{
    public LogoutResponse(){
        super();
    }

    public LogoutResponse(boolean success){
        super(success);
    }

    public LogoutResponse(boolean success, String message){
        super(success, message);
    }
}
