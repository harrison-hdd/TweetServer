package service;

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

import java.util.List;

public class StatusService {
    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest){
        checkAuthToken(postStatusRequest);
        return new PostStatusResponse(true);
    }

    public Pair<List<Status>, Boolean> getFeed(GetFeedRequest getFeedRequest){
        checkAuthToken(getFeedRequest);
        return FakeData.getInstance().getPageOfStatus(
                getFeedRequest.getLastItem(),
                getFeedRequest.getPageSize()
        );
    }

    public Pair<List<Status>, Boolean> getStory(GetStoryRequest getStoryRequest){
        checkAuthToken(getStoryRequest);
        return FakeData.getInstance().getPageOfStatus(
                getStoryRequest.getLastItem(),
                getStoryRequest.getPageSize()
        );
    }

    private void checkAuthToken(AuthenticatedRequest request){
        if(request.getAuthToken() == null){
            throw new RuntimeException("[Unauthorized]");
        }
    }
}
