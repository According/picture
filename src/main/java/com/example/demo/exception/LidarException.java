package com.example.demo.exception;

public class LidarException extends RuntimeException {

    private int code;
    private String errorMessage;
    private String[] params;

    public LidarException() {
        super();
    }

    public LidarException(String errorMessage) {
        super(errorMessage);
        this.code = 500;
        this.errorMessage = errorMessage;
    }

    public LidarException(int code, String... params) {
        //   super(errorMessage);
        this.code = code;
        this.params = params;
    }

    public LidarException(int code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
