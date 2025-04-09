package com.example.exception;

//客户端一场处理
public class CustomException extends RuntimeException {
    private String msg;

    public CustomException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
