import DAO.IDAO.IFollowDAO;
import DAO.IDAO.bean.FollowBean;
import DAOFactory.AbstractDAOFactory;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import request.authenticated_request.*;
import request.authenticated_request.paged_service_request.GetFolloweesRequest;
import request.authenticated_request.paged_service_request.GetFollowersRequest;
import response.*;
import response.paged_service_response.GetFolloweesResponse;
import response.paged_service_response.GetFollowersResponse;
import service.FollowService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

public class FollowServiceTest {
    @Test
    public void followTest(){
        UserService userService = new UserService();
        FollowService followService = new FollowService();

        LoginRequest loginRequest = new LoginRequest("@username", "password");
        LoginResponse loginResponse = userService.login(loginRequest);
        User follower = loginResponse.getUser();
        AuthToken authToken = loginResponse.getAuthToken();

        loginRequest = new LoginRequest("@username1", "password");
        loginResponse = userService.login(loginRequest);
        User followee = loginResponse.getUser();

        FollowRequest followRequest = new FollowRequest(authToken, follower, followee);
        FollowResponse followResponse = followService.follow(followRequest);

        GetFollowersCountRequest getFollowersCountRequest = new GetFollowersCountRequest(authToken, follower);
        GetFollowersCountResponse getFollowersCountResponse = followService.getFollowersCount(getFollowersCountRequest);
        GetFolloweesCountRequest getFolloweesCountRequest = new GetFolloweesCountRequest(authToken, followee);
        GetFolloweesCountResponse getFolloweesCountResponse = followService.getFolloweesCount(getFolloweesCountRequest);

        IsFollowerRequest isFollowerRequest = new IsFollowerRequest(authToken, followee, follower);
        IsFollowerResponse isFollowerResponse = followService.isFollower(isFollowerRequest);

        followRequest = new FollowRequest(authToken, followee, follower);
        followResponse = followService.follow(followRequest);



        UnfollowRequest unfollowRequest = new UnfollowRequest(authToken, follower, followee);
        UnfollowResponse unfollowResponse = followService.unfollow(unfollowRequest);
        unfollowRequest = new UnfollowRequest(authToken, followee, follower);
        unfollowResponse = followService.unfollow(unfollowRequest);
        int i = 0;
    }

    @Test
    public void insertFollows(){
//        UserService userService = new UserService();
//        FollowService followService = new FollowService();
//
//        LoginRequest loginRequest = new LoginRequest("@username", "password");
//        LoginResponse loginResponse = userService.login(loginRequest);
//        User user = loginResponse.getUser();
//        AuthToken authToken = loginResponse.getAuthToken();
//
//        loginRequest = new LoginRequest("@username1", "password");
//        loginResponse = userService.login(loginRequest);
//        User user1 = loginResponse.getUser();
//
//        List<User> users = new ArrayList<>();
//
//        for(int i = 10; i < 28; ++i){
//            String username = "@username" + i;
//            String password = "password";
//            loginRequest = new LoginRequest(username, password);
//            loginResponse = userService.login(loginRequest);
//            User _user = loginResponse.getUser();
//            FollowRequest followRequest = new FollowRequest(authToken, user, _user);
//            FollowRequest followRequest1 = new FollowRequest(authToken, _user, user1);
//            followService.follow(followRequest);
//            followService.follow(followRequest1);
//        }



    }

    @Test
    public void getFollowing(){
        UserService userService = new UserService();
        FollowService followService = new FollowService();

        LoginRequest loginRequest = new LoginRequest("@username", "password");
        LoginResponse loginResponse = userService.login(loginRequest);
        User user = loginResponse.getUser();
        AuthToken authToken = loginResponse.getAuthToken();

        loginRequest = new LoginRequest("@username1", "password");
        loginResponse = userService.login(loginRequest);
        User user1 = loginResponse.getUser();

        GetFollowersRequest getFollowersRequest = new GetFollowersRequest(authToken, user1, null, -1);
        Pair<List<User>, Boolean> result = followService.getFollowers(getFollowersRequest);

        getFollowersRequest.setLastItem(result.getFirst().get(result.getFirst().size() - 1));
        result = followService.getFollowers(getFollowersRequest);

        getFollowersRequest.setLastItem(result.getFirst().get(result.getFirst().size() - 1));
        result = followService.getFollowers(getFollowersRequest);

    }
    private FollowBean toFollowBean(User follower, User followee){
        return new FollowBean(
                follower.getAlias(),
                follower.getFirstName(),
                follower.getLastName(),
                follower.getImageUrl(),

                followee.getAlias(),
                followee.getFirstName(),
                followee.getLastName(),
                followee.getImageUrl()
        );
    }
}
