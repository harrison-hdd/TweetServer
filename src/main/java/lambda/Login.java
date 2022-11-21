package lambda;

import com.amazonaws.services.lambda.runtime.*;
import request.LoginRequest;
import response.LoginResponse;
import service.UserService;


public class Login implements RequestHandler<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse handleRequest(LoginRequest loginRequest, Context context) {
        return new UserService().login(loginRequest);
    }

}
