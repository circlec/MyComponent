package zc.commonlib.network;

import com.google.gson.annotations.SerializedName;

/**
 * @author quchao
 * @date 2018/2/12
 */

public class BaseResponse<T> {

    public static final int SUCCESS = 0;
    public static final int FAIL = -1;
    public static final int TOKEN_EXPRISE = 42002;
    /**
     * 0：成功，1：失败
     */

    @SerializedName("code")
    private int result;
    @SerializedName("msg")
    private String message;
    @SerializedName("data")
    private T value;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
