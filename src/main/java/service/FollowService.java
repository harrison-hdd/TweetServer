package service;

import DAO.IDAO.IFollowDAO;
import DAO.IDAO.IUserDAO;
import DAO.IDAO.bean.AuthTokenBean;
import DAO.IDAO.bean.FollowBean;
import DAO.IDAO.bean.UserBean;
import DAOFactory.AbstractDAOFactory;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;
import request.authenticated_request.*;
import request.authenticated_request.paged_service_request.GetFolloweesRequest;
import request.authenticated_request.paged_service_request.GetFollowersRequest;
import response.*;
import service.utils.AuthTokenValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FollowService {

    public Pair<List<User>, Boolean> getFollowers(GetFollowersRequest getFollowersRequest){
        AuthTokenValidator.validate(getFollowersRequest.getAuthToken());
        if(getFollowersRequest.getTargetUser() == null){
            throw new RuntimeException("[Bad Request] no target user provided");
        }

        IFollowDAO followDAO = AbstractDAOFactory.factory().followDAO();
        String lastFollowerHandler = null;
        if(getFollowersRequest.getLastItem() != null){
            lastFollowerHandler = getFollowersRequest.getLastItem().getAlias();
        }

        Pair<List<FollowBean>, Boolean> result = followDAO.findFollowersPagedList(getFollowersRequest.getTargetUser().getAlias(),
                getFollowersRequest.getPageSize(), lastFollowerHandler);

        List<User> followers = new ArrayList<>();
        result.getFirst().forEach((bean)->{
            followers.add( new User(bean.getFollower_first_name(),
                    bean.getFollower_last_name(),
                    bean.getFollower_handle(),
                    bean.getFollower_profile_pic_link()));
        });
        return new Pair<>(followers, result.getSecond());
    }

    public Pair<List<User>, Boolean> getFollowees(GetFolloweesRequest getFolloweesRequest){
        AuthTokenValidator.validate(getFolloweesRequest.getAuthToken());
        if(getFolloweesRequest.getTargetUser() == null){
            throw new RuntimeException("[Bad Request] no target user provided");
        }

        IFollowDAO followDAO = AbstractDAOFactory.factory().followDAO();

        String lastFolloweeHandler = null;
        if(getFolloweesRequest.getLastItem() != null){
            lastFolloweeHandler = getFolloweesRequest.getLastItem().getAlias();
        }

        Pair<List<FollowBean>, Boolean> result = followDAO.findFollowingPagedList(getFolloweesRequest.getTargetUser().getAlias(),
                getFolloweesRequest.getPageSize(), lastFolloweeHandler);

        List<User> followees = new ArrayList<>();

        result.getFirst().forEach((bean)->{
             followees.add( new User(bean.getFollowee_first_name(),
                    bean.getFollowee_last_name(),
                    bean.getFollowee_handle(),
                    bean.getFollowee_profile_pic_link()));
        });
        return new Pair<>(followees, result.getSecond());
    }

    public FollowResponse follow(FollowRequest followRequest){
        AuthTokenValidator.validate(followRequest.getAuthToken());

        FollowBean followBean = new FollowBean(
                followRequest.getFollower().getAlias(),
                followRequest.getFollower().getFirstName(),
                followRequest.getFollower().getLastName(),
                followRequest.getFollower().getImageUrl(),

                followRequest.getFollowee().getAlias(),
                followRequest.getFollowee().getFirstName(),
                followRequest.getFollowee().getLastName(),
                followRequest.getFollowee().getImageUrl()
        );
        boolean insertSuccess = AbstractDAOFactory.factory().followDAO().insert(followBean);

        //update follower and following counts
        if(insertSuccess) {
            IUserDAO userDAO = AbstractDAOFactory.factory().userDAO();
            userDAO.updateFollowersCount(followRequest.getFollowee().getAlias(), 1);
            userDAO.updateFollowingCount(followRequest.getFollower().getAlias(), 1);
        }

        return new FollowResponse(true);
    }

    public UnfollowResponse unfollow(UnfollowRequest unfollowRequest){
        AuthTokenValidator.validate(unfollowRequest.getAuthToken());

        boolean removeSuccess = AbstractDAOFactory.factory().followDAO().remove(
                unfollowRequest.getFollower().getAlias(),
                unfollowRequest.getFollowee().getAlias());

        //update follower and following counts
        if(removeSuccess){
            IUserDAO userDAO = AbstractDAOFactory.factory().userDAO();
            userDAO.updateFollowersCount(unfollowRequest.getFollowee().getAlias(), -1);
            userDAO.updateFollowingCount(unfollowRequest.getFollower().getAlias(), -1);
        }
        return new UnfollowResponse(true);
    }

    public GetFollowersCountResponse getFollowersCount(GetFollowersCountRequest getFollowersCountRequest){
        AuthTokenValidator.validate(getFollowersCountRequest.getAuthToken());
        if(getFollowersCountRequest.getTargetUser() == null){
            throw new RuntimeException("[Bad Request] no target user provided");
        }

        UserBean userBean = AbstractDAOFactory.factory().userDAO().find(getFollowersCountRequest.getTargetUser().getAlias());
        if(userBean == null){
            throw new RuntimeException("[Bad Request] user doesn't exist");
        }

        return new GetFollowersCountResponse(true, null, userBean.getFollowers_count());
    }

    public GetFolloweesCountResponse getFolloweesCount(GetFolloweesCountRequest getFolloweesCountRequest){
        AuthTokenValidator.validate(getFolloweesCountRequest.getAuthToken());
        if(getFolloweesCountRequest.getTargetUser() == null){
            throw new RuntimeException("[Bad Request] no target user provided");
        }
        UserBean userBean = AbstractDAOFactory.factory().userDAO().find(getFolloweesCountRequest.getTargetUser().getAlias());
        if(userBean == null){
            throw new RuntimeException("[Bad Request] user doesn't exist");
        }

        return new GetFolloweesCountResponse(true, null, userBean.getFollowing_count());
    }

    public IsFollowerResponse isFollower(IsFollowerRequest isFollowerRequest){
        AuthTokenValidator.validate(isFollowerRequest.getAuthToken());

        FollowBean followBean = AbstractDAOFactory.factory().followDAO().find(
                isFollowerRequest.getFollower().getAlias(),
                isFollowerRequest.getFollowee().getAlias());

        return new IsFollowerResponse(true, null, followBean != null);
    }
}
