package lambda;

import com.amazonaws.services.lambda.runtime.*;
import request.RegisterRequest;
import response.RegisterResponse;
import service.UserService;

public class Register implements RequestHandler<RegisterRequest, RegisterResponse> {
    private static final String BUCKET_NAME = "cs340tweeter-profile-pictures";
    private static final String IMAGE_FORMAT = ".jpg";

    @Override
    public RegisterResponse handleRequest(RegisterRequest registerRequest, Context context) {
        return new UserService().register(registerRequest);
    }
}
