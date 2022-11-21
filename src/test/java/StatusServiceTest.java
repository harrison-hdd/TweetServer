import DAO.IDAO.IFeedDAO;
import DAO.IDAO.bean.FeedBean;
import DAO.IDAO.bean.FollowBean;
import DAOFactory.AbstractDAOFactory;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.authenticated_request.PostStatusRequest;
import request.authenticated_request.paged_service_request.GetFeedRequest;
import request.authenticated_request.paged_service_request.GetFollowersRequest;
import request.authenticated_request.paged_service_request.GetStoryRequest;
import response.LoginResponse;
import response.PostStatusResponse;
import response.paged_service_response.GetFeedResponse;
import response.paged_service_response.GetStoryResponse;
import service.FollowService;
import service.StatusService;
import service.UserService;
import service.utils.StatusParser;

import java.util.List;

public class StatusServiceTest {
    @Test
    public void feedInsertTest(){
        UserService userService = new UserService();
        FollowService followService = new FollowService();

//        LoginRequest loginRequest = new LoginRequest("@username1", "password");
//        LoginResponse loginResponse = userService.login(loginRequest);
//        User user1 = loginResponse.getUser();
//        AuthToken authToken = loginResponse.getAuthToken();
//
//        GetFollowersRequest getFollowersRequest = new GetFollowersRequest(authToken, user1, null, -1);
//        Pair<List<User>, Boolean> result = followService.getFollowers(getFollowersRequest);
//
//        IFeedDAO feedDAO = AbstractDAOFactory.factory().feedDAO();
//
//        feedDAO.insert(result.getFirst(), user1, "test @abc byu.edu");


//        for(int i = 10; i < 28; ++i){
//            LoginRequest loginRequest = new LoginRequest("@username" + i, "password");
//            LoginResponse loginResponse = userService.login(loginRequest);
//            User user1 = loginResponse.getUser();
//            AuthToken authToken = loginResponse.getAuthToken();
//
//            GetFollowersRequest getFollowersRequest = new GetFollowersRequest(authToken, user1, null, -1);
//            Pair<List<User>, Boolean> result = followService.getFollowers(getFollowersRequest);
//
//            IFeedDAO feedDAO = AbstractDAOFactory.factory().feedDAO();
//
//            feedDAO.insert(result.getFirst(), user1,"@username" + i + " test @abc byu.edu");
//        }

    }

    @Test
    public void getFeedTest(){
        UserService userService = new UserService();
        FollowService followService = new FollowService();
        StatusService statusService = new StatusService();

        LoginRequest loginRequest = new LoginRequest("@username", "password");
        LoginResponse loginResponse = userService.login(loginRequest);
        User user = loginResponse.getUser();
        AuthToken authToken = loginResponse.getAuthToken();

//        IFeedDAO feedDAO = AbstractDAOFactory.factory().feedDAO();
//
//        List<FeedBean> list = feedDAO.getFeed("@username", 10, null).getFirst();
//
//        list = feedDAO.getFeed("@username", 10, list.get(list.size() - 1).getTimestamp()).getFirst();

        GetFeedRequest getFeedRequest = new GetFeedRequest(authToken, user, null, 10);

        Pair<List<Status>, Boolean> result = statusService.getFeed(getFeedRequest);
    }

    @Test
    public void testPost(){
        UserService userService = new UserService();
        FollowService followService = new FollowService();
        StatusService statusService = new StatusService();

        LoginRequest loginRequest = new LoginRequest("@username", "password");
        LoginResponse loginResponse = userService.login(loginRequest);
        User user = loginResponse.getUser();
        AuthToken authToken = loginResponse.getAuthToken();

        Status status = StatusParser.parse("@username1 test string @username byu.edu", user);

        PostStatusRequest postStatusRequest = new PostStatusRequest(authToken, status);
        PostStatusResponse postStatusResponse = statusService.postStatus(postStatusRequest);
//
//        for(int i = 0; i < 6; ++i){
//            Status status = StatusParser.parse("@username1 test" + i + " string @username byu.edu", user);
//
//            PostStatusRequest postStatusRequest = new PostStatusRequest(authToken, status);
//            PostStatusResponse postStatusResponse = statusService.postStatus(postStatusRequest);
//        }


    }

    @Test
    public void getStoryTest(){
        UserService userService = new UserService();
        FollowService followService = new FollowService();
        StatusService statusService = new StatusService();

        LoginRequest loginRequest = new LoginRequest("@username1", "password");
        LoginResponse loginResponse = userService.login(loginRequest);
        User user = loginResponse.getUser();
        AuthToken authToken = loginResponse.getAuthToken();

        GetStoryRequest getStoryRequest = new GetStoryRequest(authToken, user, null, 10);

        List<Status> list = statusService.getStory(getStoryRequest).getFirst();

    }


}
