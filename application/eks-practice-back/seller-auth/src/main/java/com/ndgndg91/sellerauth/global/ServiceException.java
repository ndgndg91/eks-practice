package com.ndgndg91.sellerauth.global;

public class ServiceException extends RuntimeException{
    private final int statusCode;
    private final ErrorCode errorCode;

    private final String[] additionalParams;

    public ServiceException(int statusCode, ErrorCode errorCode, String... additionalParams) {
        super(errorCode.getMessage());
        this.statusCode = statusCode;
        this.errorCode = errorCode;

        this.additionalParams = additionalParams;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() { return errorCode.name();}

    public String getErrorMessage() {
        final String additionalMessage = String.join("\t", additionalParams);
        return String.join(" : ", errorCode.getMessage(), additionalMessage);
    }
}
