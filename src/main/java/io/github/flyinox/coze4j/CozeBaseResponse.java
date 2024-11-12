package io.github.flyinox.coze4j;
import io.github.flyinox.coze4j.exception.CozeErrorCode;

// general response 
public class CozeBaseResponse<T> {
    protected Integer code;
    protected String msg;
    protected T data;

    public CozeBaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CozeBaseResponse() {
        this(0, "", null);
    }

    // get error code from response code
    public CozeErrorCode getErrorCode() {
        return CozeErrorCode.fromCode(code);
    }

    // get error code from response
    public static <ST> CozeErrorCode getErrorCode(retrofit2.Response<CozeBaseResponse<ST>> response) {
        if (response == null) {
            return CozeErrorCode.INTERNAL_SERVER_ERROR;
        }

        if (!response.isSuccessful()) {
            return CozeErrorCode.fromCode(response.code());
        }

        CozeBaseResponse<ST> body = response.body();
        if (body == null) {
            return CozeErrorCode.INTERNAL_SERVER_ERROR;
        }

        if (!body.isSuccess()) {
            return CozeErrorCode.fromCode(body.getCode());
        }

        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code == 0;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}