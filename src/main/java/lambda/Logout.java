package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import request.authenticated_request.LogoutRequest;
import response.LogoutResponse;
import service.UserService;

public class Logout implements RequestHandler<LogoutRequest, LogoutResponse> {

    @Override
    public LogoutResponse handleRequest(LogoutRequest logoutRequest, Context context) {
        return new UserService().logout(logoutRequest);
    }
}
