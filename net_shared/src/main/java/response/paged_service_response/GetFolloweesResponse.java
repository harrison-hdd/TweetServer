package response.paged_service_response;

import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class GetFolloweesResponse extends PagedServiceResponse<User> {

    public GetFolloweesResponse(){
        super();
    }

    public GetFolloweesResponse(boolean success, String message, List<User> items, boolean hasMorePages) {
        super(success, message, items, hasMorePages);
    }
}
