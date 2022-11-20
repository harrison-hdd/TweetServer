
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import request.authenticated_request.GetUserRequest;
import request.authenticated_request.LogoutRequest;
import response.GetUserResponse;
import response.LoginResponse;
import response.LogoutResponse;
import response.RegisterResponse;
import service.UserService;

public class UserServiceTest {

    @Test
    public void loginTest(){
        LoginRequest loginRequest = new LoginRequest("@username", "password");

        LoginResponse loginResponse = new UserService().login(loginRequest);

        int i = 0;
    }

    @Test
    public void registerTest(){
        RegisterRequest registerRequest = new RegisterRequest("@username", "password",
                "First1", "Last1", "abc");

        RegisterResponse registerResponse = new UserService().register(registerRequest);

        int i = 0;
    }

    @Test
    public void getUserTest(){
        LoginRequest loginRequest = new LoginRequest("@username", "password");
        LoginResponse loginResponse = new UserService().login(loginRequest);
        AuthToken authToken = loginResponse.getAuthToken();
        GetUserRequest getUserRequest = new GetUserRequest(authToken, "@username11");
        GetUserResponse getUserResponse = new UserService().getUser(getUserRequest);
        User user = getUserResponse.getUser();
    }

    @Test
    public void logoutTest(){
        LoginRequest loginRequest = new LoginRequest("@username", "password");
        LoginResponse loginResponse = new UserService().login(loginRequest);
        AuthToken authToken = loginResponse.getAuthToken();
        LogoutRequest logoutRequest = new LogoutRequest(authToken);
        LogoutResponse logoutResponse = new UserService().logout(logoutRequest);
        logoutResponse = new UserService().logout(logoutRequest);
        int i = 0;
    }
}
