package service;

import DAO.IDAO.IFeedDAO;
import DAO.IDAO.IFollowDAO;
import DAO.IDAO.IStoryDAO;
import DAO.IDAO.bean.FeedBean;
import DAO.IDAO.bean.FollowBean;
import DAO.IDAO.bean.StoryBean;
import DAO.IDAO.bean.UserBean;
import DAOFactory.AbstractDAOFactory;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageBatchResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.gson.Gson;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;
import request.authenticated_request.AuthenticatedRequest;
import request.authenticated_request.PostStatusRequest;
import request.authenticated_request.paged_service_request.GetFeedRequest;
import request.authenticated_request.paged_service_request.GetStoryRequest;
import response.PostStatusResponse;
import response.paged_service_response.GetFeedResponse;
import service.utils.AuthTokenValidator;
import service.utils.StatusParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatusService {
    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest){
        AuthTokenValidator.validate(postStatusRequest.getAuthToken());
        long timestamp = new Date(postStatusRequest.getStatus().datetime).getTime();

        StoryBean storyBean = new StoryBean(postStatusRequest.getStatus().user.getAlias(), postStatusRequest.getStatus().post, timestamp);
        AbstractDAOFactory.factory().storyDAO().insert(storyBean);

        //push request on queue
        String requestJson = new Gson().toJson(postStatusRequest);
        final String queueURL = "https://sqs.us-east-1.amazonaws.com/652355629952/PostRequests";
        final AmazonSQS client = AmazonSQSClientBuilder.defaultClient();
        SendMessageRequest request = new SendMessageRequest().withQueueUrl(queueURL)
                .withMessageBody(requestJson);

        client.sendMessage(request);

//        List<FeedBean> feedBeans = new ArrayList<>();
//        List<FollowBean> followBeans = AbstractDAOFactory.factory()
//                .followDAO()
//                .findFollowersPagedList(postStatusRequest.getStatus().user.getAlias(), -1, null)
//                .getFirst();
//
//        for(FollowBean followBean: followBeans){
//            FeedBean feedBean = new FeedBean(
//                    followBean.getFollower_handle(),
//                    postStatusRequest.getStatus().user.getAlias(),
//                    postStatusRequest.getStatus().user.getFirstName(),
//                    postStatusRequest.getStatus().user.getLastName(),
//                    postStatusRequest.getStatus().user.getImageUrl(),
//                    postStatusRequest.getStatus().post,
//                    timestamp
//            );
//            feedBeans.add(feedBean);
//        }
//
//        AbstractDAOFactory.factory().feedDAO().insert(feedBeans);

        return new PostStatusResponse(true);
    }

    public Pair<List<Status>, Boolean> getFeed(GetFeedRequest getFeedRequest){
        AuthTokenValidator.validate(getFeedRequest.getAuthToken());

        if(getFeedRequest.getTargetUser() == null){
            throw new RuntimeException("[Bad Request] no target user provided");
        }

        IFeedDAO feedDAO = AbstractDAOFactory.factory().feedDAO();

        Long lastTimestamp = null;
        if(getFeedRequest.getLastItem() != null){
            lastTimestamp = new Date(getFeedRequest.getLastItem().getDate()).getTime();
        }

        Pair<List<FeedBean>, Boolean> result = feedDAO.getFeed(getFeedRequest.getTargetUser().getAlias(),
                getFeedRequest.getPageSize(), lastTimestamp);

        List<Status> statuses = StatusParser.parse(result.getFirst());

        return new Pair<>(statuses, result.getSecond());
    }

    public Pair<List<Status>, Boolean> getStory(GetStoryRequest getStoryRequest){
        AuthTokenValidator.validate(getStoryRequest.getAuthToken());

        if(getStoryRequest.getTargetUser() == null){
            throw new RuntimeException("[Bad Request] no target user provided");
        }

        IStoryDAO storyDAO = AbstractDAOFactory.factory().storyDAO();
        Long lastTimestamp = null;
        if(getStoryRequest.getLastItem() != null){
            lastTimestamp = new Date(getStoryRequest.getLastItem().getDate()).getTime();
        }

        Pair<List<StoryBean>, Boolean> result = storyDAO.get(getStoryRequest.getTargetUser().getAlias(),
                getStoryRequest.getPageSize(), lastTimestamp);
        List<Status> statuses = StatusParser.parse(result.getFirst(), getStoryRequest.getTargetUser());

        return new Pair<>(statuses, result.getSecond());
    }

}
