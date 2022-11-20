package response;

import java.io.Serializable;

public abstract class Response implements Serializable {
    protected boolean success;
    protected String message;

    protected Response(){}

    protected Response(boolean success){
        this(success, null);
    }

    protected Response(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
