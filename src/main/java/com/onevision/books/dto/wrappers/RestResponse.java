package com.onevision.books.dto.wrappers;

public class RestResponse<T> {

    private int error = 0;
    private String errMessage = null;
    private T data = null;

    public static final RestResponse<Object> ERROR_SERVICE_UNAVAILABLE = new RestResponse<>(500, "Service unavailable");
    public static final RestResponse<Object> INTERNAL_SERVER_ERROR = new RestResponse<>(503, "The server cannot fulfill the request");
    public static final RestResponse<Object> ERROR_WRONG_PARAMETERS = new RestResponse<>(400, "Wrong parameters");
    public static final RestResponse<Object> ERROR_NOT_FOUND = new RestResponse<>(404, "Not found");
    public static final RestResponse<Object> ERROR_FORBIDDEN = new RestResponse<>(403, "Not allowed");
    public static final RestResponse<Object> OK = new RestResponse<>(0, "ok");

    public RestResponse(int error, String errMessage)
    {
        this.error = error;
        this.errMessage = errMessage;
    }

    public RestResponse(T o)
    {
        this.data = o;
    }

    public RestResponse(T o, String errMessage)
    {
        this.data = o;
        this.errMessage = errMessage;
    }

    public RestResponse(T o, int error, String errMessage)
    {
        this.data = o;
        this.error = error;
        this.errMessage = errMessage;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
