package request.authenticated_request.paged_service_request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import request.authenticated_request.AuthenticatedRequest;

public abstract class PagedServiceRequest<T> extends AuthenticatedRequest {
    protected User targetUser;
    protected T lastItem;
    protected int pageSize;

    protected PagedServiceRequest(){}

    protected PagedServiceRequest(AuthToken authToken, User targetUser, T lastItem, int pageSize) {
        super(authToken);
        this.lastItem = lastItem;
        this.targetUser = targetUser;
        this.pageSize = pageSize;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public T getLastItem() {
        return lastItem;
    }

    public void setLastItem(T lastItem) {
        this.lastItem = lastItem;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
