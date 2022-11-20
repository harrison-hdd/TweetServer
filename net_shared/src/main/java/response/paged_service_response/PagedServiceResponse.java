package response.paged_service_response;

import response.Response;

import java.util.List;

public abstract class PagedServiceResponse<T> extends Response {
    protected List<T> items;
    protected boolean hasMorePages;

    protected PagedServiceResponse(){}

    protected PagedServiceResponse(List<T> items, boolean hasMorePages) {
        this(true, null, items, hasMorePages);
    }

    protected PagedServiceResponse(boolean success, String message, List<T> items, boolean hasMorePages){
        super(success, message);
        this.items = items;
        this.hasMorePages = hasMorePages;
    }


    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public boolean getHasMorePages() {//AWS can only access getters starting with "get" or "is"
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }
}
