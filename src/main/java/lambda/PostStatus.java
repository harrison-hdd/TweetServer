package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import request.authenticated_request.PostStatusRequest;
import response.PostStatusResponse;
import service.StatusService;

public class PostStatus implements RequestHandler<PostStatusRequest, PostStatusResponse> {
    @Override
    public PostStatusResponse handleRequest(PostStatusRequest postStatusRequest, Context context) {
        return new StatusService().postStatus(postStatusRequest);
    }
}
