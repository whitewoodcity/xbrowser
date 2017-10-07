package com.whitewoodcity.core.exception;

public class InflateException extends RuntimeException{

    public InflateException() {
        super();
    }

    public InflateException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public InflateException(String detailMessage) {
        super(detailMessage);
    }

    public InflateException(Throwable throwable) {
        super(throwable);
    }
}
