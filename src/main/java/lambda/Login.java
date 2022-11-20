package lambda;

import com.amazonaws.services.lambda.runtime.*;
import request.LoginRequest;
import response.LoginResponse;
import service.UserService;


public class Login implements RequestHandler<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse handleRequest(LoginRequest loginRequest, Context context) {
//        System.out.println(loginRequest.getUsername());
//        System.out.println(loginRequest.getPassword());
        return new UserService().login(loginRequest);
    }

    public static void main(String[] args){
        LoginResponse res = new Login().handleRequest(new LoginRequest("a", "a"),
                new Context() {
            @Override
            public String getAwsRequestId() {
                return null;
            }

            @Override
            public String getLogGroupName() {
                return null;
            }

            @Override
            public String getLogStreamName() {
                return null;
            }

            @Override
            public String getFunctionName() {
                return null;
            }

            @Override
            public String getFunctionVersion() {
                return null;
            }

            @Override
            public String getInvokedFunctionArn() {
                return null;
            }

            @Override
            public CognitoIdentity getIdentity() {
                return null;
            }

            @Override
            public ClientContext getClientContext() {
                return null;
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 0;
            }

            @Override
            public int getMemoryLimitInMB() {
                return 0;
            }

            @Override
            public LambdaLogger getLogger() {
                return null;
            }
        });

        int i = 0;
    }
}
