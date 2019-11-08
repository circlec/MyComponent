package zc.commonlib.network;

/**
 * @author quchao
 * @date 2017/11/27
 */

public class ServiceException extends Exception {

    private int result;
    private String message;

    public ServiceException(String message, int result) {
        super(message);
        this.message = message;
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message == null ? "" : message;
    }
}
