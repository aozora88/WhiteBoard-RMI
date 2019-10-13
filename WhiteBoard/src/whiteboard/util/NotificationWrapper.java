package whiteboard.util;

import java.io.Serializable;

public class NotificationWrapper<T> implements Serializable {

    private static final long serialVersionUID = 4290458457330577774L;
    private boolean result;
    private String message;
    private T data;

    public NotificationWrapper() {
        super();
    }

    public NotificationWrapper(boolean result, String message) {
        super();
        this.result = result;
        this.message = message;
    }
 
    public NotificationWrapper(boolean result, String message, T data) {
        super();
        this.result = result;
        this.message = message;
        this.data = data;
    }

    /**
     * @return the result
     */
    public boolean isResult() {
        return result;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param result the result to set
     */
    public void setResult(boolean result) {
        this.result = result;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }
}