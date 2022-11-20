package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import request.authenticated_request.GetUserRequest;
import response.GetUserResponse;
import service.UserService;

public class GetUser implements RequestHandler<GetUserRequest, GetUserResponse> {

    @Override
    public GetUserResponse handleRequest(GetUserRequest getUserRequest, Context context) {
        return new UserService().getUser(getUserRequest);
    }
}
